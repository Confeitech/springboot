package br.com.confeitech.mappers;

import br.com.confeitech.dto.UserDTO;
import br.com.confeitech.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userModelToUserDTO(UserModel user);

    UserModel userDTOToUserModel(UserDTO userDTO);

}
