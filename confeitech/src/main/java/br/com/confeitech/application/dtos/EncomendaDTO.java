package br.com.confeitech.application.dtos;

import br.com.confeitech.domain.enums.AndamentoEncomenda;
import br.com.confeitech.domain.models.CakeModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EncomendaDTO(

        Long id,

        @NotNull(message = "O preço é um campo obrigatório")
        Double preco,
        String observacoes,
        @NotNull(message = "O bolo é um campo obrigatório")
        Long bolo,
        @Size(max = 255, message = "Mensagem de adicionais longa demais!")
        String adicionais,
        AndamentoEncomenda andamento,
        LocalDate data
) {
}
