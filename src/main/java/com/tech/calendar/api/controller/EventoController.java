package com.tech.calendar.api.controller;

import com.tech.calendar.api.DTO.CadastrarEventoDTO;
import com.tech.calendar.api.DTO.DadosListagemEventos;
import com.tech.calendar.api.DTO.IDUsuarioDTO;
import com.tech.calendar.api.domain.evento.Evento;
import com.tech.calendar.api.domain.evento.EventoRepository;
import com.tech.calendar.api.domain.usuario.UsuarioRepository;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastrarEventoDTO dados){
        var evento = new Evento(dados);
        eventoRepository.save(evento);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemEventos>> listar(@PageableDefault(sort = {"dataInicio"})Pageable pageable){
        var page = eventoRepository.findAllByAtivoTrue(pageable).map(DadosListagemEventos::new);
        System.out.println(page);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id){
        var evento = eventoRepository.getReferenceById(id);
        evento.excluir();

        return ResponseEntity.ok("Evento: " + evento.getNome() + " excluído");
    }

    @PostMapping("/{idEvento}/inscricao")
    public ResponseEntity adicionarInscrito(@RequestBody IDUsuarioDTO idUsuario, @PathVariable Long idEvento){
        var evento = eventoRepository.getReferenceById(idEvento);
        var usuario = usuarioRepository.getReferenceById(idUsuario.id());

        evento.adicionarInscrito(usuario);
        eventoRepository.save(evento);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuário: " + usuario.getNome() + " inscrito no evento: " + evento.getNome());
    }
}
