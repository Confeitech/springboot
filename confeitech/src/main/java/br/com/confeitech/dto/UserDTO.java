package br.com.confeitech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

import java.time.LocalDate;

public record UserDTO(

        @JsonProperty("nome")
        String name,
        String email,
        @JsonProperty("senha")
        String password,
        @JsonProperty("telefone")
        String phone,
        @JsonProperty("dtNasc")
        LocalDate birthDate

) {
}
