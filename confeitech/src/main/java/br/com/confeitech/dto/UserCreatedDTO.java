package br.com.confeitech.dto;

public record UserCreatedDTO(

        String name,
        String email,
        String phone,
        String birthDate
) {
}
