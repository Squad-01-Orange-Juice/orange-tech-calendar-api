package com.tech.calendar.api.controller;

import com.tech.calendar.api.DTO.CadastrarEventoDTO;
import com.tech.calendar.api.DTO.DadosListagemEventos;
import com.tech.calendar.api.domain.evento.Evento;
import com.tech.calendar.api.domain.evento.EventoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarEventoDTO dados){
        var evento = new Evento(dados);
        eventoRepository.save(evento);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemEventos>> listar(@PageableDefault(sort = {"data"})Pageable pageable){
        var page = eventoRepository.findAllByAtivoTrue(pageable).map(DadosListagemEventos::new);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id){
        var evento = eventoRepository.getReferenceById(id);
        evento.excluir();

        return ResponseEntity.ok("Evento: " + evento.getNome() + " excluído");
    }
}
