package br.com.confeitech.infra.persistence.mappers;


import br.com.confeitech.application.dtos.EncomendaDTO;
import br.com.confeitech.application.dtos.EncomendaExibicaoDTO;
import br.com.confeitech.domain.models.CakeModel;
import br.com.confeitech.domain.models.EncomendaModel;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CakeMapper.class})
public interface EncomendaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bolo", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataRetirada", ignore = true)
    @Mapping(target = "andamento", ignore = true)
    @Mapping(target = "user", ignore = true)
    EncomendaModel encomendaDTOToEncomendaModel(EncomendaDTO encomendaDTO);

}
