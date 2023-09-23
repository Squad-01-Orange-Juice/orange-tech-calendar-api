package com.tech.calendar.api.DTO;

import com.tech.calendar.api.domain.evento.Tipo;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CadastrarEventoDTO(
        @NotBlank
        String nome,
        @NotNull
        @Future
        LocalDateTime data,
        boolean online,
        boolean gratuito,
        double preco,
        @NotNull
        Tipo tipo
) {
}
