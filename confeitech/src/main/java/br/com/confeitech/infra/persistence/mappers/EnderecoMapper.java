package br.com.confeitech.infra.persistence.mappers;

import br.com.confeitech.application.dtos.EnderecoDTO;
import br.com.confeitech.domain.models.EnderecoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {


    @Mapping(source = "logradouro", target = "address")
    @Mapping(source = "bairro", target = "neighborhood")
    EnderecoModel enderecoDTOToEnderecoModel(EnderecoDTO enderecoDTO);

    EnderecoDTO enderecoModelToEnderecoDTO(EnderecoModel enderecoModel);
}
