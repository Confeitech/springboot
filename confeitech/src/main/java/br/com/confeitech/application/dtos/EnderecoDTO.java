package br.com.confeitech.application.dtos;

import lombok.Getter;

public record EnderecoDTO(
        String cep,
        String logradouro,
        String bairro,
        String uf
) {}

