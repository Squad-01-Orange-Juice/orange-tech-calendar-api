package com.tech.calendar.api.domain.usuario;

import com.tech.calendar.api.DTO.RegistrarUsuarioDTO;
import com.tech.calendar.api.domain.evento.Evento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UsuarioTeste {

    @Test
    void testeConstrutorDTO(){
        Usuario usuario = new Usuario(dadosUsuario("Andre@email.com",
                "Andre", "senha", UserRole.ADMIN));
        Assertions.assertNotNull(usuario);
        Assertions.assertEquals("Andre", usuario.getNome());
    }

    @Test
    void testeMetodoGetPassword(){
        Usuario usuario = new Usuario(dadosUsuario("Andre@email.com",
                "Andre", "senha", UserRole.ADMIN));
        Assertions.assertEquals("senha", usuario.getPassword());
    }

    @Test
    void deveRetornarTrueQuandoOUsuarioSeInscreve(){
        Usuario usuario = new Usuario(dadosUsuario("Andre@email.com",
                "Andre", "senha", UserRole.ADMIN));

        Evento evento = new Evento();
        usuario.getEventosInscritos().add(evento);
        Assertions.assertTrue(usuario.getEventosInscritos().contains(evento));
    }

    private RegistrarUsuarioDTO dadosUsuario(String login,
                                             String nome,
                                             String password,
                                             UserRole userRole){
        return new RegistrarUsuarioDTO(login, nome, password, userRole);
    }
}
