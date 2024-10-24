package br.com.confeitech.application.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@JsonPropertyOrder({"id", "nome", "peso", "preco", "descricao", "contemGluten", "contemLactose", "ativo", "adicionais"})
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
        List<AdicionalDTO> adicionais,

        @JsonProperty("ativo")
        Boolean active
) {
}
