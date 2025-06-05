package br.com.confeitech.application.dtos;

import br.com.confeitech.domain.models.UserModel;

import java.util.List;

public record DashboardGraficoDTO(

        String movimentacaoSemana,
        Long qtdEncomendasUltimoMes,
        Long qtdEncomendasConcluidasUltimoMes,
        Integer qtdEncomendasNaoAceitasSemana,
        String boloMaisVendido,
        List<Integer> graficoVendasSemana,
        List<Integer> graficoVendidoSemana

) {}
