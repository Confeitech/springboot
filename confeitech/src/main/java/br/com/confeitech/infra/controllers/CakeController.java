package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.CakeDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.domain.models.CakeModel;
import br.com.confeitech.domain.services.CakeService;
import br.com.confeitech.domain.services.UserService;
import br.com.confeitech.infra.persistence.repositories.CakeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.confeitech.application.utils.MessageUtils.CAKE_NOT_FOUND;

@RestController
@RequestMapping("/cakes")
@CrossOrigin(origins = "*")
public class CakeController {

    @Autowired
    private CakeService cakeService;

    @Autowired
    private CakeRepository repository;

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



    // atualiza a foto de uma planta
    // "consumes" indica o tipo de dado que será aceito no corpo da requisição
    // o mime-type indicado no "consumes" é image/*, que indica que qualquer imagem será aceita
    // uma lista dos tipos de mime-type está em https://mimetype.io/all-types/ ou em https://www.sitepoint.com/mime-types-complete-list/
    @CrossOrigin("*")
    @PatchMapping(value = "/imagem/{id}", consumes = "image/*")
    public ResponseEntity<Void> patchFoto(@PathVariable Long id, @RequestBody byte[] novaFoto) {
        if (!repository.existsById(id)) {
            throw new ApplicationExceptionHandler(CAKE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        repository.setImagem(id, novaFoto);

        return ResponseEntity.status(200).build();
    }




    // recupera a foto de uma planta
    // "produces" indica o tipo de dado que será entregue no corpo da resposta
    // o mime-type indicado no "produces" é image/jpeg (MediaType.IMAGE_JPEG_VALUE), mas, na prática, qualquer imagem funcionará
    // uma lista dos tipos de mime-type está em https://mimetype.io/all-types/ ou em https://www.sitepoint.com/mime-types-complete-list/
    @GetMapping(value = "/imagem/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImagem(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ApplicationExceptionHandler(CAKE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        byte[] foto = repository.getImagem(id);

        // esse header "content-disposition" indica o nome do arquivo em caso de download em navegador
        return ResponseEntity.status(200).header("content-disposition",
                "attachment; filename=\"foto-planta.jpg\"").body(foto);
    }
}
