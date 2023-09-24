package com.tech.calendar.api.domain.evento;

import com.tech.calendar.api.DTO.CadastrarEventoDTO;
import com.tech.calendar.api.DTO.RegistrarUsuarioDTO;
import com.tech.calendar.api.domain.usuario.UserRole;
import com.tech.calendar.api.domain.usuario.Usuario;
import com.tech.calendar.api.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EventoRepositoryTeste {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void deveVerificarOFindAllByAtivoTrue(){
        var evento = new Evento(dadosEvento("Orange hackathon", "Orange hackathon - MVC calendário",
                "Brasil", "", LocalDateTime.now(), LocalDateTime.now().plusHours(12), true, 0,
                Tipo.hackathon, Modalidade.online));

        var evento2 = new Evento(dadosEvento("Orange hackathon 2", "Orange hackathon - MVC calendário",
                "Brasil", "", LocalDateTime.now(), LocalDateTime.now().plusHours(12), true, 0,
                Tipo.hackathon, Modalidade.online));

        evento2.excluir();

        em.persistAndFlush(evento);
        em.persistAndFlush(evento2);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Evento> eventos = eventoRepository.findAllByAtivoTrue(pageable);

        assertThat(eventos).isNotEmpty();
        assertThat(eventos.getTotalElements()).isEqualTo(1);
        assertThat(eventos.getContent().get(0)).isEqualTo(evento);
    }

    @Test
    void deveVerificarOFindByInscritos_Id(){
        Evento evento1 = new Evento(dadosEvento("Orange hackathon", "Orange hackathon - MVC calendário",
                "Brasil", "", LocalDateTime.now(), LocalDateTime.now().plusHours(12), true, 0,
                Tipo.hackathon, Modalidade.online));
        Evento evento2 = new Evento(dadosEvento("Orange hackathon 2", "Orange hackathon - MVC calendário",
                "Brasil", "", LocalDateTime.now(), LocalDateTime.now().plusHours(12), true, 0,
                Tipo.hackathon, Modalidade.online));

        em.persistAndFlush(evento1);
        em.persistAndFlush(evento2);

        Usuario usuario = new Usuario(dadosUsuario("Andre@email.com",
                "Andre", "senha", UserRole.ADMIN));

        em.persistAndFlush(usuario);

        evento1.getInscritos().add(usuario);
        evento2.getInscritos().add(usuario);
        em.flush();

        Pageable pageable = PageRequest.of(0, 10);

        Page<Evento> eventosInscritos = eventoRepository.findByInscritos_Id(usuario.getId(), pageable);

        assertThat(eventosInscritos).isNotEmpty();
        assertThat(eventosInscritos.getTotalElements()).isEqualTo(2);
        assertThat(eventosInscritos.getContent().get(1).getNome()).isEqualTo("Orange hackathon 2");
    }

    private CadastrarEventoDTO dadosEvento(String nome,
                                           String descricao,
                                           String local,
                                           String link,
                                           LocalDateTime dataInicio,
                                           LocalDateTime dataFinal,
                                           boolean gratuito,
                                           double preco,
                                           Tipo tipo,
                                           Modalidade modalidade){
        return new CadastrarEventoDTO(nome, descricao, local, link, dataInicio, dataFinal, gratuito, preco,
                tipo, modalidade);
    }

    private RegistrarUsuarioDTO dadosUsuario(String login,
                                             String nome,
                                             String password,
                                             UserRole userRole){
        return new RegistrarUsuarioDTO(login, nome, password, userRole);
    }
}
