package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.EmailRequest;
import br.com.confeitech.application.dtos.UserCreatedDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.application.utils.EmailService;
import br.com.confeitech.domain.models.UserModel;
import br.com.confeitech.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @GetMapping
    public ResponseEntity<List<UserCreatedDTO>> getUser() {
        userService.fodase("doiszerodoiscinco@gmail.com", "scortuzzi@gmail.com", "kryi stwg wyya hbni", "smtp.gmail.com", "587");
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCreatedDTO> getUserPerId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @GetMapping("/login")
    public ResponseEntity<UserCreatedDTO> getUserByEmailAndPassword(@RequestParam String email, @RequestParam String password) {
        return ResponseEntity.ok().body(userService.buscarPorEmailESenha(email, password));
    }

    @GetMapping("/getAlphabetical")
    public ResponseEntity<List<UserCreatedDTO>> getUsersPerAlphabeticalOrder() {
        return ResponseEntity.ok().body(userService.getUserPerAlphabeticalOrder());
    }

    @PostMapping
    public ResponseEntity<UserCreatedDTO> saveUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserCreatedDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enviarEmail(@RequestBody EmailRequest request) {
        userService.fodase(request.des(), "scortuzzi@gmail.com", "kryi stwg wyya hbni", "smtp.gmail.com", "587");
    }


}
