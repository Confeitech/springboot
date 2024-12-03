package br.com.confeitech.Controller;

import br.com.confeitech.application.dtos.UserCreatedDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.domain.services.UserService;
import br.com.confeitech.infra.controllers.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()  // Allow `/users` endpoint to be accessed without authentication
                    .anyRequest().permitAll()         // Require authentication for all other requests
                    .and()
                    .csrf().disable();  // Disable CSRF for testing purposes (avoid issues with form submissions)
            return http.build();
        }
    }

    @Test
    @DisplayName("Verifica o primeiro usuário e retorna 200")
    void getAllUsersTest() throws Exception {
        List<UserCreatedDTO> usuarios = List.of(
                new UserCreatedDTO(
                        1L,
                        "Larisso",
                        "larisso@confeitech.com",
                        "11945200901",
                        LocalDate.parse("2000-01-01"),
                        true
                ),
                new UserCreatedDTO(
                        2L,
                        "Pietro brás",
                        "pietro@confeitech.com",
                        "11945200901",
                        LocalDate.parse("2000-01-01"),
                        true
                )
        );


        when(userService.getUsers()).thenReturn(usuarios);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Larisso"))
                .andExpect(jsonPath("$[0].email").value("larisso@confeitech.com"))
                .andExpect(jsonPath("$[0].phone").value("11945200901"))
                .andExpect(jsonPath("$[0].birthDate").value("2000-01-01"))
                .andExpect(jsonPath("$[0].active").value(true));
    }

    @Test
    @DisplayName("Dado que tenho um usuário pelo id, retorna status 200")
    void getUserPerIdTest() throws Exception {
        UserCreatedDTO user = new UserCreatedDTO(
                2L,
                "Pietro brás",
                "pietro@confeitech.com",
                "11945200901",
                LocalDate.parse("2000-01-01"),
                true
        );

        when(userService.getUser(2L)).thenReturn(user);

        mockMvc.perform(get("/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Pietro brás"))
                .andExpect(jsonPath("$.email").value("pietro@confeitech.com"))
                .andExpect(jsonPath("$.phone").value("11945200901"))
                .andExpect(jsonPath("$.birthDate").value("2000-01-01"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Dado que tenho uma empresa para salvar, retorna status 200")
    void saveUser() throws Exception {

        String json = """
                {
                	"nome": "nome",
                	"email": "teste5@gmail.com",
                	"senha": "senhaTeste123",
                	"telefone": "11945200901",
                	"dtNasc": "2000-01-01",
                	"cep": "03526020"
                }
                """;

        UserCreatedDTO user = new UserCreatedDTO(
                1L,
                "nome",
                "teste5@gmail.com",
                "11945200901",
                LocalDate.of(2000, 1, 1),
                true
        );

        when(userService.saveUser(Mockito.any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("nome"))
                .andExpect(jsonPath("$.email").value("teste5@gmail.com"))
                .andExpect(jsonPath("$.phone").value("11945200901"))
                .andExpect(jsonPath("$.birthDate").value("2000-01-01"))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Dado que a requisição contém dados inválidos, retorna status 400")
    void saveUserWithInvalidData() throws Exception {

        // Invalid JSON: invalid email format
        String json = """
            {
                "nome": "nome",
                "email": "invalidEmail",
                "senha": "senhaTeste123",
                "telefone": "11945200901",
                "dtNasc": "2000-01-01",
                "cep": "03526020"
            }
            """;

        // Mocking an invalid email scenario
        when(userService.saveUser(Mockito.any(UserDTO.class)))
                .thenThrow(new ApplicationExceptionHandler("padrão de email incorreto", HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request  .andExpect(jsonPath("$.message").value("padrão de email incorreto"));  // Custom message for validation error
    }

}
