package com.tech.calendar.api.DTO;

import com.tech.calendar.api.domain.evento.Evento;

public record EventosInscritosDTO(String nome) {
    public EventosInscritosDTO(Evento evento){
        this(evento.getNome());
    }
}
