package br.com.confeitech.services;

import br.com.confeitech.dto.UserCreatedDTO;
import br.com.confeitech.dto.UserDTO;
import br.com.confeitech.mappers.UserMapper;
import br.com.confeitech.models.UserModel;
import br.com.confeitech.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserCreatedDTO saveUser(UserDTO userDTO) {

        if(userRepository.existsByEmail(userDTO.email())) {
            throw new ValidationException("Email já cadastrado");
        }

        UserModel userModel = userMapper.userDTOToUserModel(userDTO);

        return insertUser(userModel, userDTO);

    }

    public UserCreatedDTO updateUser(Long userId, UserDTO userDTO) {

        UserModel user = userRepository.getReferenceById(userId);
        return insertUser(user, userDTO);
    }

    public UserCreatedDTO insertUser(UserModel user, UserDTO userDTO) {

        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPhone(userDTO.phone());
        user.setBirthDate(LocalDate.parse(userDTO.birthDate()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);

        userRepository.save(user);

        return userMapper.userModelToUserCreatedDTO(user);
    }


}
