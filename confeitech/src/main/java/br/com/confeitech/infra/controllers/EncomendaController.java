package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.AndamentoDTO;
import br.com.confeitech.application.dtos.CakeDTO;
import br.com.confeitech.application.dtos.EncomendaDTO;
import br.com.confeitech.domain.enums.AndamentoEncomenda;
import br.com.confeitech.domain.services.EncomendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encomendas")
public class EncomendaController {

    @Autowired
    private EncomendaService encomendaService;

    @GetMapping
    public ResponseEntity<List<EncomendaDTO>> getEncomendas() {
        return ResponseEntity.ok().body(encomendaService.getEncomendas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncomendaDTO> getEncomendaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(encomendaService.getEncomendaPerId(id));
    }

    @PostMapping
    public ResponseEntity<EncomendaDTO> saveEncomenda(@RequestBody @Valid EncomendaDTO encomendaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(encomendaService.saveEncomenda(encomendaDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EncomendaDTO> updateAndamentoEncomenda(@PathVariable Long id, @RequestBody AndamentoDTO andamentoEncomenda) {
        return ResponseEntity.ok(encomendaService.alterarAndamentoDaEncomenda(andamentoEncomenda, id));
    }
}
