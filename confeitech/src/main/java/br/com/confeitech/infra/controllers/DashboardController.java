package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.DashboardGraficoDTO;
import br.com.confeitech.domain.models.Dashboard;
import br.com.confeitech.domain.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping()
    public ResponseEntity<DashboardGraficoDTO> getGraficos() {

        return ResponseEntity.ok().body(dashboardService.getDashboard());
    }
}
