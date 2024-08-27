package br.com.confeitech.services;

import br.com.confeitech.dto.UserDTO;
import br.com.confeitech.mappers.UserMapper;
import br.com.confeitech.models.UserModel;
import br.com.confeitech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    public UserDTO saveUser(UserDTO userDTO) {

        UserModel user = userMapper.userDTOToUserModel(userDTO);
        user.setBirthDate(LocalDate.now());

        userRepository.save(user);

        return userMapper.userModelToUserDTO(user);


    }

}
