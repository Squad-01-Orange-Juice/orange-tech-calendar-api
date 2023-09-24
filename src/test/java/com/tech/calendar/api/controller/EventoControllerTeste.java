package com.tech.calendar.api.controller;

import com.tech.calendar.api.DTO.CadastrarEventoDTO;
import com.tech.calendar.api.domain.evento.Evento;
import com.tech.calendar.api.domain.evento.EventoRepository;
import com.tech.calendar.api.domain.evento.Modalidade;
import com.tech.calendar.api.domain.evento.Tipo;
import com.tech.calendar.api.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EventoControllerTeste {

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private EventoController eventoController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testeCadastrarEvento(){
        CadastrarEventoDTO dados  = dadosEvento("Orange hackathon", "Orange hackathon - MVC calendário",
                "Brasil", "", LocalDateTime.now(), LocalDateTime.now().plusHours(12), true, 0,
                Tipo.hackathon, Modalidade.online);
        Evento evento = new Evento(dados);

        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        ResponseEntity<?>response = eventoController.cadastrar(dados);

        Assertions.assertEquals(ResponseEntity.ok().build(), response);
    }

    @Test
    void testeExcluirEvento(){
        Long idEvento = 1L;
        Evento evento = new Evento(dadosEvento("Orange hackathon", "Orange hackathon - MVC calendário",
                "Brasil", "", LocalDateTime.now(), LocalDateTime.now().plusHours(12), true, 0,
                Tipo.hackathon, Modalidade.online));

        when(eventoRepository.getReferenceById(idEvento)).thenReturn(evento);

        ResponseEntity<?> response = eventoController.excluir(idEvento);

        Assertions.assertEquals(ResponseEntity.ok("Evento: " + evento.getNome() + " excluído"), response);
        verify(eventoRepository, times(1)).getReferenceById(idEvento);
        verify(eventoRepository, times(1)).save(evento);
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
}
