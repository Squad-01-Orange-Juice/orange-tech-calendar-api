package com.tech.calendar.api.domain.usuario;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);

    Usuario findByTokenDeResetDeSenha(String token);

    @Query("SELECT u.id FROM Usuario u WHERE u.login = :login")
    Long findUserIdByEmail(String login);
}
