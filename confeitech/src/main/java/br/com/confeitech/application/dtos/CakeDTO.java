package br.com.confeitech.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CakeDTO(

        Long id,
        @NotBlank(message = "O nome do bolo é um campo obrigatório")
        @Size(max = 255, message = "Nome do bolo longo demais!")
        @JsonProperty("nome")
        String name,
        @NotNull(message = "O peso do bolo é um campo obrigatório")
        @JsonProperty("peso")
        Double weight,
        @NotNull(message = "O preço do bolo é um campo obrigatório")
        @JsonProperty("preco")
        Double price,
        @JsonProperty("descricao")
        String description,
        @JsonProperty("contemGluten")
        Boolean containsGluten,
        @JsonProperty("contemLactose")
        Boolean containsLactose,
        @JsonProperty("ativo")
        Boolean active

) {
}
