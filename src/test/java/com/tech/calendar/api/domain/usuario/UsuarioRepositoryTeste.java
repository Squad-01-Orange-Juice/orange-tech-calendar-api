package com.tech.calendar.api.domain.usuario;

import com.tech.calendar.api.domain.evento.EventoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UserDetails;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTeste {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void deveVerificarOFindByLogin(){
        Usuario usuario = new Usuario("andre@email.com", "Andre", "password", UserRole.USER);
        em.persistAndFlush(usuario);

        UserDetails userDetails = usuarioRepository.findByLogin("andre@email.com");

        Assertions.assertNotNull(userDetails);
    }

    @Test
    void testeFindByTokenDeResetDeSenha(){
        Usuario usuario = new Usuario("andre@email.com", "Andre", "password", UserRole.USER);
        em.persistAndFlush(usuario);

        usuario.setTokenDeResetDeSenha("1234");
        em.persistAndFlush(usuario);

        Usuario buscarUsuario = usuarioRepository.findByTokenDeResetDeSenha("1234");

        Assertions.assertNotNull(buscarUsuario);
        Assertions.assertEquals("1234", buscarUsuario.getTokenDeResetDeSenha());

    }
}
