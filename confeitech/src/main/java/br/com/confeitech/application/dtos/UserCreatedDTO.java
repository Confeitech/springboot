package br.com.confeitech.application.dtos;

import java.time.LocalDate;

public record UserCreatedDTO(

        Long id,
        String name,
        String email,
        String phone,
        LocalDate birthDate,
        Boolean active
) {
}
