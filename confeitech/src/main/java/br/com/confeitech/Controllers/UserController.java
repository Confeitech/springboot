package br.com.confeitech.Controllers;

import br.com.confeitech.dto.UserDTO;
import br.com.confeitech.mappers.UserMapper;
import br.com.confeitech.repository.UserRepository;
import br.com.confeitech.services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(id, userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUser() {
        ResponseEntity.ok().body(userService.getUsers);
    }
}
