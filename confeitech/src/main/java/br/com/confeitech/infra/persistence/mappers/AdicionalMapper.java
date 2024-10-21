package br.com.confeitech.infra.persistence.mappers;

import br.com.confeitech.application.dtos.AdicionalDTO;
import br.com.confeitech.domain.models.AdicionalModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdicionalMapper {

    AdicionalDTO AdicionalModelToAdicionalDTO(AdicionalModel adicionaisModel);

    AdicionalModel adicionalDTOToAdicionalModel(AdicionalDTO adicionaisDTO);
}