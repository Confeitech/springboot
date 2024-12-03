package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.UserCreatedDTO;
import br.com.confeitech.domain.models.Dashboard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {


    @GetMapping
    public ResponseEntity<Dashboard> getDashboard() {
        return ResponseEntity.ok().body(new Dashboard());
    }
}
