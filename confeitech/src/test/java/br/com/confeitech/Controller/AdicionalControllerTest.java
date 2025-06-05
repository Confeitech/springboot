package br.com.confeitech.Controller;

import br.com.confeitech.application.dtos.AdicionalDTO;
import br.com.confeitech.application.dtos.UserCreatedDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.domain.services.AdicionalService;
import br.com.confeitech.domain.services.UserService;
import br.com.confeitech.infra.controllers.AdicionalController;
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

@WebMvcTest(AdicionalController.class)
public class AdicionalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private br.com.confeitech.domain.services.AdicionalService AdicionalService;

    @Mock
    private AdicionalController AdicionalController;

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
    @DisplayName("Verifica o primeiro adicional e retorna 200")
    void getAllAdicionaisTest() throws Exception {
        List<AdicionalDTO> adicionais = List.of(
                new AdicionalDTO(
                        1L,
                        "Morango",
                        2.0,
                        true
                ),
                new AdicionalDTO(
                        2L,
                        "Baunilha",
                        3.0,
                        true
                )
        );


        when(AdicionalService.getAdicionais()).thenReturn(adicionais);

        mockMvc.perform(get("/adicionais"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Morango"))
                .andExpect(jsonPath("$[0].preco").value(2.0))
                .andExpect(jsonPath("$[0].active").value(true));
    }

    @Test
    @DisplayName("Dado que tenho um adicional pelo id, retorna status 200")
    void getAdicionalPerIdTest() throws Exception {
        AdicionalDTO adicional = new AdicionalDTO(
                1L,
                "Morango",
                2.0,
                true
        );

        when(AdicionalService.getAdicionalPorId(1L)).thenReturn(adicional);

        mockMvc.perform(get("/adicionais/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Morango"))
                .andExpect(jsonPath("$.preco").value(2.0))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Dado que tenho um adicional para salvar, retorna status 200")
    void saveAdicional() throws Exception {

        String json = """
                {
                	"nome": "cenoura",
                	"preco": 5.0
                }
                """;

        AdicionalDTO adicional = new AdicionalDTO(
                1L,
                "Morango",
                2.0,
                true
        );


        when(AdicionalService.saveAdicionais(Mockito.any(AdicionalDTO.class))).thenReturn(adicional);

        mockMvc.perform(post("/adicionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Morango"))
                .andExpect(jsonPath("$.preco").value(2.0))
                .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Dado que a requisição contém dados inválidos, retorna status 400")
    void saveAdicionalWithInvalidData() throws Exception {

        // Invalid JSON: invalid email format
        String json = """
                {
                	"preco": 5.0
                }
            """;

        // Mocking an invalid email scenario
        when(AdicionalService.saveAdicionais(Mockito.any(AdicionalDTO.class)))
                .thenThrow(new ApplicationExceptionHandler("O nome é um campo obrigatório", HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/adicionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request  .andExpect(jsonPath("$.message").value("padrão de email incorreto"));  // Custom message for validation error
    }

}
