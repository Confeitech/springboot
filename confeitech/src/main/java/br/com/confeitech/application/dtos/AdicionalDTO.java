package br.com.confeitech.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdicionalDTO(

        Long id,

        @NotBlank(message = "O nome é um campo obrigatório")
        @Size(max = 255, message = "Nome longo demais!")
        @JsonProperty("nome")
        String nome,
        @JsonProperty("preco")
        Double preco,

        Boolean active

) {
}