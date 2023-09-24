package com.tech.calendar.api.DTO;

import com.tech.calendar.api.evento.domain.Modalidade;
import com.tech.calendar.api.evento.domain.Tipo;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CadastrarEventoDTO(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotBlank
        String local,
        @NotBlank
        String link,
        @NotNull
        @Future
        LocalDateTime dataInicio,
        @NotNull
        @Future
        LocalDateTime dataFinal,
        boolean gratuito,
        double preco,
        @NotNull
        Tipo tipo,
        @NotNull
        Modalidade modalidade
) {
}
