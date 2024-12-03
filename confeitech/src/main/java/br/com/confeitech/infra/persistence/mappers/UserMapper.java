package br.com.confeitech.infra.persistence.mappers;

import br.com.confeitech.application.dtos.UserCreatedDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.domain.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userModelToUserDTO(UserModel user);

    @Mapping(constant = "true", target = "active")
    UserModel userDTOToUserModel(UserDTO userDTO);

//    @Mapping(target = "password", ignore = true)
    @Mapping(source = "id",target = "id")
    UserCreatedDTO userModelToUserCreatedDTO(UserModel userModel);

    UserModel userCreatedToUserModel(UserCreatedDTO userCreatedDTO);
}
