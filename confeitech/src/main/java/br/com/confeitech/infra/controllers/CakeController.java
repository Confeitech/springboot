package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.CakeDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.domain.models.CakeModel;
import br.com.confeitech.domain.services.CakeService;
import br.com.confeitech.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cakes")
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @GetMapping
    public ResponseEntity<List<CakeDTO>> getCakes() {
        return ResponseEntity.ok().body(cakeService.getCakes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CakeDTO> getCakePerId(@PathVariable Long id) {
        return ResponseEntity.ok().body(cakeService.getCake(id));
    }

    @GetMapping("/getAlphabetical")
    public ResponseEntity<List<CakeDTO>> getCakesPerAlphabeticalOrder() {
        return ResponseEntity.ok().body(cakeService.getCakesByAlfabeticalOrder());
    }

    @GetMapping("/getName")
    public ResponseEntity<CakeDTO> getCakePerName(@RequestParam String nome) {
        return ResponseEntity.ok().body(cakeService.buscarBoloPorNome(nome.trim()));
    }

    @PostMapping
    public ResponseEntity<CakeDTO> saveCake(@RequestBody @Valid CakeDTO cakeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cakeService.saveCake(cakeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CakeDTO> udpateCake(@PathVariable Long id, @Valid @RequestBody CakeDTO cakeDTO) {
        return ResponseEntity.ok(cakeService.updateCake(id, cakeDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCake(@PathVariable Long id) {
        cakeService.deleteCake(id);
    }
}
