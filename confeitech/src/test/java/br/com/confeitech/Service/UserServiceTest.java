package br.com.confeitech.Service;

import br.com.confeitech.application.dtos.UserCreatedDTO;
import br.com.confeitech.domain.models.UserModel;
import br.com.confeitech.domain.services.UserService;
import br.com.confeitech.infra.persistence.repositories.UserRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock // dublê - a nossa "repository" vai ser um dublê
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // Vamos testar o método "buscarTodos"


    @Test
    @DisplayName("Dado que, tenho algo no banco, retorna lista com empresas")
    void buscarTodosListaCheia() {
        // GIVEN
//        List<UserModel> usuarios = List.of(
//    new UserModel(
//            1L,
//            "Pietro brás",
//            "pietro@confeitech.com",
//            "senha",
//            "11 945200901",
//            LocalDate.parse("2000-01-01"),
//            true),
//                new UserModel(
//                        2L,
//                        "SPTECO",
//                        "Educare",
//                        "senha2",
//                        "11 951938872",
//                        LocalDate.parse("2000-02-02"),
//                        true
//                ));

        List<UserCreatedDTO> usuarios = List.of(
                new UserCreatedDTO(
                        1L,
                        "Larisso",
                        "larisso@confeitech.com",
                        "11 945200901",
                        LocalDate.parse("2000-01-01"),
                        true
                ),
                new UserCreatedDTO(
                        2L,
                        "Pietro brás",
                        "pietro@confeitech.com",
                        "11 945200901",
                        LocalDate.parse("2000-01-01"),
                        true
                )
        );



        // WHEN
        Mockito.when(userRepository.findAll()).thenReturn(userService.mapearParaModel(usuarios));

        // THEN
        List<UserCreatedDTO> resultado = userService.getUsers();

        // ASSERT

        assertNotNull(resultado);
        assertEquals(usuarios.size(), resultado.size());
//        assertEquals(empresas.get(0).getId(), resultado.get(0).getId());
//        assertEquals(empresas.get(1).getId(), resultado.get(1).getId());

        for (int i = 0; i < resultado.size(); i++) {

            UserCreatedDTO UsuarioEsperado = usuarios.get(i);
            UserCreatedDTO UsuarioRetornado = resultado.get(i);

            assertEquals(UsuarioEsperado.id(), UsuarioRetornado.id());
            assertEquals(UsuarioEsperado.name(), UsuarioRetornado.name());
            assertEquals(UsuarioEsperado.email(), UsuarioRetornado.email());
            assertEquals(UsuarioEsperado.active(), UsuarioRetornado.active());
            assertEquals(UsuarioEsperado.phone(), UsuarioRetornado.phone());
            assertEquals(UsuarioEsperado.birthDate(), UsuarioRetornado.birthDate());

        }

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }



}
