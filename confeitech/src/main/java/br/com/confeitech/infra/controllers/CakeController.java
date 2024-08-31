package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.CakeDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.domain.services.CakeService;
import br.com.confeitech.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cakes")
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @GetMapping
    public ResponseEntity<?> getCakes() {
        return ResponseEntity.ok().body(cakeService.getCakes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCakePerId(@PathVariable Long id) {
        return ResponseEntity.ok().body(cakeService.getCake(id));
    }

    @PostMapping
    public ResponseEntity<?> saveCake(@RequestBody @Valid CakeDTO cakeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cakeService.saveCake(cakeDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> udpateCake(@PathVariable Long id, @Valid @RequestBody CakeDTO cakeDTO) {
        return ResponseEntity.ok(cakeService.updateCake(id, cakeDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCake(@PathVariable Long id) {
        cakeService.deleteCake(id);
    }
}
