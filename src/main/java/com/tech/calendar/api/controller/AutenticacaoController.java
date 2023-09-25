package com.tech.calendar.api.controller;

import com.tech.calendar.api.DTO.*;
import com.tech.calendar.api.domain.usuario.Usuario;
import com.tech.calendar.api.domain.usuario.UsuarioRepository;
import com.tech.calendar.api.infra.email.EmailSenderService;
import com.tech.calendar.api.infra.security.ResetarSenhaService;
import com.tech.calendar.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ResetarSenhaService reset;

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody @Valid RegistrarUsuarioDTO dados){
        if(this.usuarioRepository.findByLogin(dados.login()) != null) return ResponseEntity.badRequest().build();

        String senhaEncriptada = new BCryptPasswordEncoder().encode(dados.password());
        Usuario novoUsuario = new Usuario(dados.login(), dados.nome(), senhaEncriptada, dados.userRole());

        this.usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoDTO dados){
        var usuarioESenha = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        var auth = this.authenticationManager.authenticate(usuarioESenha);
        var token = this.tokenService.gerarToken((Usuario) auth.getPrincipal());
        Long id = this.usuarioRepository.findUserIdByEmail(dados.login());
        return ResponseEntity.ok(new RespostaLoginDTO(token, id));
    }

    @PostMapping("/esqueceu-senha")
    public ResponseEntity esqueceuSenha(@RequestBody EsqueceuSenhaDTO dto){
        if(usuarioRepository.findByLogin(dto.email()) == null) throw new RuntimeException("Email não encontrado");
        Usuario usuario = (Usuario) usuarioRepository.findByLogin(dto.email());

        String token = reset.gerarToken(usuario);
        emailSenderService.sendPassword(dto.email(), token);
        return ResponseEntity.ok("Email para recuperação de senha enviado");
    }

    @PostMapping("resetar-senha/{token}")
    public ResponseEntity resetarSenha(@RequestBody NovaSenhaDTO dto, @PathVariable String token){
        if(reset.tokenValido(token)) {
            reset.redefinirSenha(token, dto.novaSenha());
            return ResponseEntity.ok("Senha redefinida");
        } else return ResponseEntity.badRequest().body("Token inválido");
    }
}
