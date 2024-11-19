package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.AndamentoDTO;
import br.com.confeitech.application.dtos.CarrinhoDTO;
import br.com.confeitech.application.dtos.EncomendaDTO;
import br.com.confeitech.domain.services.CarrinhoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinhos")
@CrossOrigin(origins = "*")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public ResponseEntity<List<CarrinhoDTO>> getCarrinhos() {
        return ResponseEntity.ok().body(carrinhoService.getCarrinhos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarrinhoDTO> getCarrinhoPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(carrinhoService.getCarrinhoPorId(id));
    }

//    @PostMapping
//    public ResponseEntity<CarrinhoDTO> saveEncomenda(@RequestBody @Valid CarrinhoDTO carrinhoDTO) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoService.saveCarrinho(carrinhoDTO));
//    }

}
