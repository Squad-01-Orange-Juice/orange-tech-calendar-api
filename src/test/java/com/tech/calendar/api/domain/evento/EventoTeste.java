package com.tech.calendar.api.domain.evento;


import com.tech.calendar.api.domain.usuario.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;

public class EventoTeste {

    @Test
    void deveRetornarFalseQuandoEventoEstaInativo(){
        Evento evento = new Evento(1L, "Orange hackathon", "Orange hackathon - MVC calendário", "Brasil",
                "", LocalDateTime.now(), LocalDateTime.now().plusHours(12), Modalidade.online, true,
                0, true, Tipo.hackathon, null);

        Assertions.assertTrue(evento.isAtivo());

        evento.excluir();

        Assertions.assertFalse(evento.isAtivo());
    }

    @Test
    void deveRetornarTrueQuandoOEventoTemInscrito(){
        Evento evento = new Evento(1L, "Orange hackathon", "Orange hackathon - MVC calendário", "Brasil",
                "", LocalDateTime.now(), LocalDateTime.now().plusHours(12), Modalidade.online, true,
                0, true, Tipo.hackathon, new HashSet<>());

        Usuario usuario = new Usuario();

        evento.getInscritos().add(usuario);
        Assertions.assertTrue(evento.getInscritos().contains(usuario));
    }

}
