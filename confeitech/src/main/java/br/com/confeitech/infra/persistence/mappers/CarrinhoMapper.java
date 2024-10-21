package br.com.confeitech.infra.persistence.mappers;

import br.com.confeitech.application.dtos.CarrinhoDTO;
import br.com.confeitech.domain.models.CakeModel;
import br.com.confeitech.domain.models.CarrinhoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarrinhoMapper {

    @Mapping(target = "dataCompra", ignore = true)
    @Mapping(target = "encomendas", ignore = true)
    @Mapping(target = "id", ignore = true)
    CarrinhoModel carrinhoDTOToCarrinhoModel(CarrinhoDTO carrinhoDTO);

    @Mapping(target = "encomendas", ignore = true)
    CarrinhoDTO carrinhoModelToCarrinhoDTO(CarrinhoModel carrinhoModel);
}
