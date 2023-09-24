package com.tech.calendar.api.domain.evento;

import com.tech.calendar.api.DTO.CadastrarEventoDTO;
import com.tech.calendar.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String local;
    private String link;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;

    @Enumerated
    private Modalidade modalidade;

    private boolean gratuito;
    private double preco;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;


    @ManyToMany
    @JoinTable(
            name = "inscricao",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> inscritos = new HashSet<>();

    public Evento(CadastrarEventoDTO dados){
        this.ativo = true;
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.local = dados.local();
        this.link = dados.link();
        this.dataInicio = dados.dataInicio();
        this.dataFinal = dados.dataFinal();
        this.modalidade = dados.modalidade();
        this.gratuito = dados.gratuito();
        if(dados.preco() != 0) this.preco = dados.preco();
        else this.preco = 0;
        this.tipo = dados.tipo();
    }

    public void excluir(){
        this.ativo = false;
    }

    public void adicionarInscrito(Usuario usuario){
        this.inscritos.add(usuario);
        usuario.getEventosInscritos().add(this);

    }
}