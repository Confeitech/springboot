package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.UserCreatedDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.domain.services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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

    @GetMapping
    public ResponseEntity<List<UserCreatedDTO>> getUser() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCreatedDTO> getUserPerId(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUser(id));
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

}
