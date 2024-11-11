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
@CrossOrigin(origins = "*")
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

    @GetMapping("/gerarCSV")
    public ResponseEntity<String> gerarCSV() {

        encomendaService.gerarCSV();
        return ResponseEntity.ok().body("Relatório CSV criado com sucesso!");
    }

    @GetMapping("/aceitas")
    public ResponseEntity<List<EncomendaDTO>> getEncomendasAceitas() {
        return ResponseEntity.ok().body(encomendaService.getEncomendasAceitas());
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
