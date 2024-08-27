package br.com.confeitech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CakeDTO(
        @JsonProperty("nome")
        String name,
        @JsonProperty("peso")
        Double weight,
        @JsonProperty("preco")
        Double price,
        @JsonProperty("descricao")
        String description,
        @JsonProperty("contemGluten")
        Boolean containsGluten,
        @JsonProperty("contemLactose")
        Boolean containsLactose

) {
}
