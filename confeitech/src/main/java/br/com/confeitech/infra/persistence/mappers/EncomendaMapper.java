package br.com.confeitech.infra.persistence.mappers;


import br.com.confeitech.application.dtos.EncomendaDTO;
import br.com.confeitech.domain.models.EncomendaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EncomendaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bolo", ignore = true)
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "andamento", ignore = true)
    EncomendaModel encomendaDTOToEncomendaModel(EncomendaDTO encomendaDTO);

    @Mapping(target = "bolo", source = "bolo.id")
    EncomendaDTO encomendaModelToEncomendaDTO(EncomendaModel encomendaModel);

}
