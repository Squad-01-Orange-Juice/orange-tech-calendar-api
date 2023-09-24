package com.tech.calendar.api.infra.security;

import com.tech.calendar.api.domain.usuario.Usuario;
import com.tech.calendar.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResetarSenhaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public String gerarToken(Usuario usuario){
        String token = UUID.randomUUID().toString();

        usuario.setTokenDeResetDeSenha(token);
        usuarioRepository.save(usuario);

        return token;
    }

    public boolean tokenValido(String token){
        Usuario usuario = buscarUsuario(token);

        if(usuario == null) return false;

        return true;
    }

    public void redefinirSenha(String token, String novaSenha){
        Usuario usuario = buscarUsuario(token);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaCriptografada = encoder.encode(novaSenha);
        usuario.setTokenDeResetDeSenha(null);
        usuario.setPassword(senhaCriptografada);
        usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuario(String token){
        return usuarioRepository.findByTokenDeResetDeSenha(token);
    }
}
