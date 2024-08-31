package br.com.confeitech.infra.persistence.mappers;

import br.com.confeitech.application.dtos.CakeDTO;
import br.com.confeitech.domain.models.CakeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CakeMapper {

    CakeDTO cakeModelToCakeDTO(CakeModel cakeModel);

    @Mapping(constant = "true", target = "active")
    CakeModel cakeDTOToCakeModel(CakeDTO cakeDTO);
}
