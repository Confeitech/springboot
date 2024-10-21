package br.com.confeitech.application.dtos;

import br.com.confeitech.domain.models.EncomendaModel;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CarrinhoDTO(

        Long id,
        @NotNull(message = "O preço total é um campo obrigatório!")
        Double precoTotal,
        String descricao,
        LocalDate dataCompra,
        @NotNull(message = "A data da retirada é um campo obrigatório!")
        LocalDateTime dataRetirada,
        @NotNull(message = "Um carrinho não pode conter nenhuma encomenda!")
        List<EncomendaDTO> encomendas

) {
}
