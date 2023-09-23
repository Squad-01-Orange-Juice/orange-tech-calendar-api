package com.tech.calendar.api.DTO;

import com.tech.calendar.api.domain.usuario.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrarUsuarioDTO(
        @Email
        String login,
        @NotBlank
        String nome,
        @NotBlank
        String password,
        UserRole userRole
) {
}
