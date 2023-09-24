package com.tech.calendar.api.domain.usuario;

import com.tech.calendar.api.DTO.RegistrarUsuarioDTO;
import com.tech.calendar.api.evento.domain.Evento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;
    private String nome;
    private String password;
    private UserRole userRole;
    private String tokenDeResetDeSenha;

    @ManyToMany(mappedBy = "inscritos")
    private Set<Evento> eventosInscritos = new HashSet<>();

    public Usuario(String login, String nome, String password, UserRole userRole){
        this.login = login;
        this.nome = nome;
        this.password = password;
        this.userRole = userRole;
    }

    public Usuario(RegistrarUsuarioDTO dados) {
        this.login = dados.login();
        this.nome = dados.nome();
        this.password = dados.password();
        this.userRole = dados.userRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userRole == UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        } else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setTokenDeResetDeSenha(String token){
        this.tokenDeResetDeSenha = token;
    }

    public void setPassword(String novaSenha){
        this.password = novaSenha;
    }
}