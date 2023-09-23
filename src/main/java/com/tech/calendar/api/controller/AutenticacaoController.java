package com.tech.calendar.api.controller;

import com.tech.calendar.api.DTO.AutenticacaoDTO;
import com.tech.calendar.api.DTO.RegistrarUsuarioDTO;
import com.tech.calendar.api.DTO.RespostaLoginDTO;
import com.tech.calendar.api.domain.usuario.Usuario;
import com.tech.calendar.api.domain.usuario.UsuarioRepository;
import com.tech.calendar.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        return ResponseEntity.ok(new RespostaLoginDTO(token));
    }
}
