package br.com.confeitech.infra.controllers;

import br.com.confeitech.application.dtos.DashboardGraficoDTO;
import br.com.confeitech.domain.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@RestController
@RequestMapping("/csv")
public class CsvController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<byte[]> downloadCsv() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8)) {

            DashboardGraficoDTO dashboard = dashboardService.getDashboard();


            // Escrevendo dados no CSV
            writer.println("Data,Movimentação da semana,quantidade de encomendas no último mês, quantidade de encomendas Concluídas no último mês quantidade de encomendas não aceitas, bolo mais vendido");
            writer.println(
                    String.format("%s, %s, %s, %s, %s, %s",
                            String.valueOf(LocalDate.now()),
                            String.valueOf(dashboard.movimentacaoSemana()),
                            String.valueOf(dashboard.qtdEncomendasUltimoMes()),
                            String.valueOf(dashboard.qtdEncomendasConcluidasUltimoMes()),
                            String.valueOf(dashboard.qtdEncomendasNaoAceitasSemana()),
                            String.valueOf(dashboard.boloMaisVendido())
                    )
            );

            // Convertendo para byte array
            byte[] csvBytes = outputStream.toByteArray();

            // Configurando cabeçalhos HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"data.csv\"");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

            // Retornando o arquivo CSV
            return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error generating CSV: " + e.getMessage()).getBytes(StandardCharsets.UTF_8));
        }
    }
}
