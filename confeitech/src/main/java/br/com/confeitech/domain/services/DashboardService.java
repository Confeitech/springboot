package br.com.confeitech.domain.services;


import br.com.confeitech.application.dtos.DashboardGraficoDTO;
import br.com.confeitech.domain.models.CakeModel;
import br.com.confeitech.infra.persistence.repositories.EncomendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private EncomendaService encomendaService;

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private CakeService cakeService;

    public DashboardGraficoDTO getDashboard() {

        Integer qtdEncomendasNaoConfirmadas = encomendaService.getEncomendasEmAguardo().size();

        LocalDate dataInicio = LocalDate.now().minusMonths(1);
        Long qtdEncomendasEntreguesNoUltimoMes = encomendaRepository.qtdEncomendasFeitasUltimoMes(dataInicio);

        String diaDaSemanaComEncomendasMaisFrequentes = encomendaRepository.diaDeEncomendaMaisFrequenteSemana();

        String boloMaisVendidoDeTodos = cakeService.getCakePerId(encomendaRepository.boloMaisComprado()).getName();

        Long encomendasConcluidas = encomendaRepository.qtdEncomendasConcluidasUltimoMes(dataInicio);

        return new DashboardGraficoDTO(
                traduzirDiaSemana(diaDaSemanaComEncomendasMaisFrequentes),
                qtdEncomendasEntreguesNoUltimoMes,
                encomendasConcluidas,
                qtdEncomendasNaoConfirmadas,
                boloMaisVendidoDeTodos,
                List.of(2,4,9,5,3,1,8),
                List.of(8,1,3,5,9,4,2)
        );
    }

        public static String traduzirDiaSemana(String diaEmIngles) {
            // Mapeamento de dias da semana do inglês para o português
            Map<String, String> diasSemana = Map.of(
                    "Sunday", "Domingo",
                    "Monday", "Segunda-feira",
                    "Tuesday", "Terça-feira",
                    "Wednesday", "Quarta-feira",
                    "Thursday", "Quinta-feira",
                    "Friday", "Sexta-feira",
                    "Saturday", "Sábado"
            );

            // Normaliza o input para evitar erros por maiúsculas/minúsculas
            String diaNormalizado = diaEmIngles.trim().toLowerCase();

            // Busca a tradução considerando case insensitivity
            for (Map.Entry<String, String> entrada : diasSemana.entrySet()) {
                if (entrada.getKey().toLowerCase().equals(diaNormalizado)) {
                    return entrada.getValue();
                }
            }

            // Caso não encontre, retorna uma mensagem de erro
            return "Dia inválido!";
        }

//    public List<Integer> getEncomendasPorMes(int ano) {
//        LocalDate startDate = LocalDate.of(ano, 1, 1);
//        LocalDate endDate = LocalDate.of(ano, 12, 31);
//
//        List<Object[]> result = encomendaRepository.graficoMes(startDate, endDate);
//
//        // Inicializa uma lista com 12 elementos (um para cada mês)
//        List<Integer> quantidadePorMes = new ArrayList<>(Collections.nCopies(12, 0));
//
//        for (Object[] row : result) {
//            Integer mes = (Integer) row[0]; // O mês (0 para janeiro, 1 para fevereiro...)
//            Long quantidade = (Long) row[1]; // A quantidade de encomendas
//
//            quantidadePorMes.set(mes, quantidade.intValue());
//        }
//
//        return quantidadePorMes;
//    }
}
