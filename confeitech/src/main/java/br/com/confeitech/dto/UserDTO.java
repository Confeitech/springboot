package br.com.confeitech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserDTO(

        @NotBlank(message = "Nome é um campo obrigatório")
        @Size(max = 255, message = "Nome longo demais!")
        @JsonProperty("nome")
        String name,
        @NotBlank(message = "email é um campo obrigatório")
        @Size(max = 255, message = "Email longo demais!")
        @Email(message = "padrão de email incorreto")
        String email,
        @NotBlank(message = "Senha é um campo obrigatório")
        @Size(max = 255, message = "Senha longa demais!")
        @JsonProperty("senha")
        String password,
        @NotBlank(message = "Telefone é um campo obrigatório")
        @Pattern(regexp = "\\(?\\d{2}\\)?\\d?\\d{4}-?\\d{4}", message = "telefone inválido")
        @JsonProperty("telefone")
        String phone,
        @Past(message = "Dia inválido")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Padrão de data inválido, utilize o padrão yyyy/MM/dd")
        @JsonProperty("dtNasc")
        String birthDate

) {
}
