package com.tech.calendar.api.DTO;

import com.tech.calendar.api.evento.domain.Evento;

public record EventosInscritosDTO(String nome) {
    public EventosInscritosDTO(Evento evento){
        this(evento.getNome());
    }
}
