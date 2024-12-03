package br.com.confeitech.Controller;

import br.com.confeitech.application.dtos.AdicionalDTO;
import br.com.confeitech.application.dtos.CakeDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.domain.services.CakeService;
import br.com.confeitech.infra.controllers.CakeController;
import br.com.confeitech.infra.persistence.repositories.CakeRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CakeController.class)
public class CakeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CakeService cakeService;

    @Mock
    private CakeController cakeController;

    @MockBean
    private CakeRepository cakeRepository;

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

        List<AdicionalDTO> adicionais = List.of(
                new AdicionalDTO(
                        2L,
                        "Morango",
                        2.0,
                        true
                )
        );

        List<CakeDTO> cakes = List.of(
                new CakeDTO(
                        1L,
                        "bolo de chocolate",
                        1.0,
                        10.0,
                        "um bolo de chocolate muito gostoso",
                        adicionais,
                        true
                ),
                new CakeDTO(
                        2L,
                        "bolo de cenoura",
                        15.0,
                        15.0,
                        "um bolo de chocolate, só que com cenoura",
                        adicionais,
                        true
                )
        );


        when(cakeService.getCakes()).thenReturn(cakes);

        mockMvc.perform(get("/cakes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("bolo de chocolate"))
                .andExpect(jsonPath("$[0].peso").value(1.0))
                .andExpect(jsonPath("$[0].preco").value(10.0))
                .andExpect(jsonPath("$[0].descricao").value("um bolo de chocolate muito gostoso"))
                .andExpect(jsonPath("$[0].ativo").value(true));
    }

    @Test
    @DisplayName("Dado que tenho um usuário pelo id, retorna status 200")
    void getUserPerIdTest() throws Exception {

        List<AdicionalDTO> adicionais = List.of(
                new AdicionalDTO(
                        2L,
                        "Morango",
                        2.0,
                        true
                )
        );

        CakeDTO user = new CakeDTO(
                3L,
                "bolo de chocolate",
                1.0,
                10.0,
                "um bolo de chocolate muito gostoso",
                adicionais,
                true
        );

        when(cakeService.getCake(3L)).thenReturn(user);

        mockMvc.perform(get("/cakes/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.nome").value("bolo de chocolate"))
                .andExpect(jsonPath("$.peso").value(1.0))
                .andExpect(jsonPath("$.preco").value(10.0))
                .andExpect(jsonPath("$.descricao").value("um bolo de chocolate muito gostoso"))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    @Test
    @DisplayName("Dado que tenho uma empresa para salvar, retorna status 200")
    void saveUser() throws Exception {

        String json = """
            {
	            "nome": "Bolo de Cenoura com Brigadeiro",
	            "peso": 2.0,
	            "preco": 10.0,
	            "descricao": "Uma versão deliciosa do tradicional bolo de cenoura, com massa macia e vibrante, coberto com uma generosa camada de brigadeiro cremoso. Decorado com granulado de chocolate belga, é uma combinação irresistível e nostálgica que agrada a todas as idades.",
	            "adicionais": [
		            {
			            "nome": "morango",
			            "preco": 2.0
		            }
	            ]
            }
                """;

        List<AdicionalDTO> adicionais = List.of(
                new AdicionalDTO(
                        1L,
                        "Morango",
                        2.0,
                        true
                )
        );

        CakeDTO user = new CakeDTO(
                1L,
                "Bolo de Cenoura com Brigadeiro",
                2.0,
                10.0,
                "Uma versão deliciosa do tradicional bolo de cenoura, com massa macia e vibrante, coberto com uma generosa camada de brigadeiro cremoso. Decorado com granulado de chocolate belga, é uma combinação irresistível e nostálgica que agrada a todas as idades.",
                adicionais,
                true
        );

        when(cakeService.saveCake(Mockito.any(CakeDTO.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/cakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Bolo de Cenoura com Brigadeiro"))
                .andExpect(jsonPath("$.peso").value(2.0))
                .andExpect(jsonPath("$.preco").value(10.0))
                .andExpect(jsonPath("$.descricao").value("Uma versão deliciosa do tradicional bolo de cenoura, com massa macia e vibrante, coberto com uma generosa camada de brigadeiro cremoso. Decorado com granulado de chocolate belga, é uma combinação irresistível e nostálgica que agrada a todas as idades."));

    }

    @Test
    @DisplayName("Dado que a requisição contém dados inválidos, retorna status 400")
    void saveUserWithInvalidData() throws Exception {

        // Invalid JSON: invalid email format
        String json = """
            {
	            "nome": "Bolo de Cenoura com Brigadeiro",
	            "preco": 10.0,
	            "descricao": "Uma versão deliciosa do tradicional bolo de cenoura, com massa macia e vibrante, coberto com uma generosa camada de brigadeiro cremoso. Decorado com granulado de chocolate belga, é uma combinação irresistível e nostálgica que agrada a todas as idades.",
	            "adicionais": [
		            {
			            "nome": "morango",
			            "preco": 2.0
		            }
	            ]
            }
            """;

        // Mocking an invalid email scenario
        when(cakeService.saveCake(Mockito.any(CakeDTO.class)))
                .thenThrow(new ApplicationExceptionHandler("O peso do bolo é um campo obrigatório", HttpStatus.BAD_REQUEST));

        mockMvc.perform(MockMvcRequestBuilders.post("/cakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request  .andExpect(jsonPath("$.message").value("padrão de email incorreto"));  // Custom message for validation error

    }
}

