package com.tech.calendar.api.DTO;

import com.tech.calendar.api.domain.evento.Evento;
import com.tech.calendar.api.domain.evento.Tipo;

import java.time.LocalDateTime;

public record DadosListagemEventos(Long id, String nome, LocalDateTime data, boolean online, boolean gratuito,
                                   double preco, Tipo tipo) {
    public DadosListagemEventos(Evento evento){
        this(evento.getId(), evento.getNome(), evento.getData(), evento.isOnline(), evento.isGratuito(),
                evento.getPreco(), evento.getTipo());
    }
}
