package br.com.confeitech.application.dtos;

import br.com.confeitech.domain.models.UserModel;

import java.util.List;

public record DashboardGraficoDTO(

        String movimentacaoSemana,
        Integer qtdEncomendassemana,
        String boloMaisVendido,
        List<Integer> graficoVendasSemana,
        List<Integer> graficoVendidoSemana




) {


    public DashboardGraficoDTO() {
        this(
                "segunda",
                23,
                "Bolo de chocolate",
                List.of(10, 11, 16, 12, 6, 12, 10),
                List.of(10, 12, 9, 11, 8, 15, 10)
        );
    }
}
