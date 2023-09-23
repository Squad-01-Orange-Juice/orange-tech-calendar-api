package com.tech.calendar.api.controller;

import com.tech.calendar.api.DTO.IDUsuarioDTO;
import com.tech.calendar.api.domain.evento.Evento;
import com.tech.calendar.api.domain.evento.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/eventosInscritos")
    public ResponseEntity<Page<Evento>> listarEventos(@RequestBody IDUsuarioDTO idUsuario, Pageable pageable){

        var page = eventoRepository.findByInscritos_Id(idUsuario.id(), pageable);

        return ResponseEntity.ok((Page<Evento>) page);
    }
}