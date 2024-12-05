package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.DashboardGraficoDTO;
import br.com.confeitech.domain.models.Dashboard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {


    @GetMapping()
    public ResponseEntity<DashboardGraficoDTO> getGraficos() {

        DashboardGraficoDTO dashboard = new DashboardGraficoDTO();

        return ResponseEntity.ok().body(dashboard);
    }
}
