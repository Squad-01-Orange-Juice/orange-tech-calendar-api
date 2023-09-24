package com.tech.calendar.api.DTO;

import com.tech.calendar.api.domain.evento.Evento;
import com.tech.calendar.api.domain.evento.Tipo;

import java.time.LocalDateTime;

public record DadosListagemEventos(Long id, String nome, String descricao, String local, String link,
                                   LocalDateTime dataInicio, LocalDateTime dataFinal,
                                   String modalidade, boolean gratuito, double preco, Tipo tipo) {
    public DadosListagemEventos(Evento evento){
        this(evento.getId(), evento.getNome(), evento.getDescricao(), evento.getLocal(), evento.getLink(),
                evento.getDataInicio(), evento.getDataFinal(), evento.getModalidade().toString(),
                evento.isGratuito(), evento.getPreco(), evento.getTipo());
    }
}
