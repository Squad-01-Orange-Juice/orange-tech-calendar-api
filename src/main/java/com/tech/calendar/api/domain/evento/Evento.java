package com.tech.calendar.api.domain.evento;

import com.tech.calendar.api.DTO.CadastrarEventoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LocalDateTime data;
    private boolean online;
    private boolean gratuito;
    private double preco;
    private boolean ativo;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    public Evento(CadastrarEventoDTO dados){
        this.ativo = true;
        this.nome = dados.nome();
        this.data = dados.data();
        this.online = dados.online();
        this.gratuito = dados.gratuito();
        if(dados.preco() != 0) this.preco = dados.preco();
        else this.preco = 0;
        this.tipo = dados.tipo();
    }

    public void excluir(){
        this.ativo = false;
    }

}
