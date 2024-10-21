package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.AdicionalDTO;
import br.com.confeitech.domain.services.AdicionalService;
import br.com.confeitech.infra.persistence.repositories.AdicionalRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adicionais")
public class AdicionalController {

    @Autowired
    private AdicionalService adicionaisService;

    @GetMapping
    public ResponseEntity<List<AdicionalDTO>> getAdicionais() {
        return ResponseEntity.ok().body(adicionaisService.getAdicionais());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdicionalDTO> getAdicionalPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(adicionaisService.getAdicionalPorId(id));
    }

    @PostMapping
    public ResponseEntity<AdicionalDTO> saveAdicional(@RequestBody @Valid AdicionalDTO adicionaisDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adicionaisService.saveAdicionais(adicionaisDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdicionalDTO> updateAdicional(@PathVariable Long id, @Valid @RequestBody AdicionalDTO adicionalDTO) {
        return ResponseEntity.ok(adicionaisService.updateAdicional(id, adicionalDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdicional(@PathVariable Long id) {
        adicionaisService.deleteAdicional(id);
    }

}