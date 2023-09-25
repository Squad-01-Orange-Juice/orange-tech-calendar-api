package com.tech.calendar.api.DTO;

import com.tech.calendar.api.domain.evento.Evento;

import java.time.LocalDateTime;

public record EventosInscritosDTO(String nome, LocalDateTime dataInicio, LocalDateTime dataFinal) {
    public EventosInscritosDTO(Evento evento){
        this(evento.getNome(), evento.getDataInicio(), evento.getDataFinal());
    }
}
