package br.com.confeitech.application.dtos;

import br.com.confeitech.domain.enums.AndamentoEncomenda;
import br.com.confeitech.domain.models.EncomendaModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EncomendaExibicaoDTO(

        Long id,
        Double preco,
        String observacoes,
        CakeDTO bolo,
        String adicionais,
        AndamentoEncomenda andamento,
        LocalDate dataCriacao,
        LocalDate dataRetirada,
        UserDTO userDTO
) {
    public EncomendaExibicaoDTO(EncomendaModel encomendaModel) {
        this(
                encomendaModel.getId(),
                encomendaModel.getPreco(),
                encomendaModel.getObservacoes(),
                new CakeDTO(encomendaModel.getBolo()),
                encomendaModel.getAdicionais(),
                encomendaModel.getAndamento(),
                encomendaModel.getDataCriacao(),
                encomendaModel.getDataRetirada(),
                new UserDTO(encomendaModel.getUser())
        );
    }
}
