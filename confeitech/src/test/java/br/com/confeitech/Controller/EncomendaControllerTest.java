package br.com.confeitech.Controller;

import br.com.confeitech.application.dtos.*;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.domain.enums.AndamentoEncomenda;
import br.com.confeitech.infra.controllers.EncomendaController;
import br.com.confeitech.infra.persistence.repositories.CakeRepository;
import br.com.confeitech.infra.persistence.repositories.EncomendaRepository;
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

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EncomendaController.class)
public class EncomendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private br.com.confeitech.domain.services.EncomendaService EncomendaService;

    @Mock
    private EncomendaController EncomendaController;

    @MockBean
    private br.com.confeitech.infra.persistence.repositories.EncomendaRepository EncomendaRepository;

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
    @DisplayName("Verifica a primeira encomenda e retorna 200")
    void getAllEncomendasTest() throws Exception {

        List<AdicionalDTO> adicionais = List.of(
                new AdicionalDTO(
                        2L,
                        "Morango",
                        2.0,
                        true
                )
        );

        CakeDTO cake = new CakeDTO(
                3L,
                "bolo de chocolate",
                1.0,
                10.0,
                "um bolo de chocolate muito gostoso",
                adicionais,
                true
        );

        UserDTO user = new UserDTO(
                "Pietro brás",
                "123123",
                "pietro@confeitech.com",
                "11945200901",
                "2000-01-01",
                "03526020",
                true
        );

        List<EncomendaExibicaoDTO> encomendas = List.of(
                new EncomendaExibicaoDTO(
                        1L,
                        10.0,
                        "Sem pimenta por favor",
                        cake,
                        "morango e chocolate",
                        AndamentoEncomenda.AGUARDANDO,
                        LocalDate.now(),
                        LocalDate.parse("2025-10-17"),
                        user
                ),
                new EncomendaExibicaoDTO(
                        2L,
                        15.0,
                        "Nenhuma observação",
                        cake,
                        "cenoura apimentada",
                        AndamentoEncomenda.AGUARDANDO,
                        LocalDate.now(),
                        LocalDate.parse("2025-10-17"),
                        user
                )
        );


        when(EncomendaService.getEncomendas()).thenReturn(encomendas);

        mockMvc.perform(get("/encomendas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].preco").value(15.0))
                .andExpect(jsonPath("$[1].observacoes").value("Nenhuma observação"))
                .andExpect(jsonPath("$[1].andamento").value("AGUARDANDO"))
                .andExpect(jsonPath("$[1].dataCriacao").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[1].dataRetirada").value("2025-10-17"));
    }

    @Test
    @DisplayName("Dado que tenho uma encomenda pelo id, retorna status 200")
    void getUserPerIdTest() throws Exception {

        List<AdicionalDTO> adicionais = List.of(
                new AdicionalDTO(
                        2L,
                        "Morango",
                        2.0,
                        true
                )
        );

        CakeDTO cake = new CakeDTO(
                3L,
                "bolo de chocolate",
                1.0,
                10.0,
                "um bolo de chocolate muito gostoso",
                adicionais,
                true
        );

        UserDTO user = new UserDTO(
                "Pietro brás",
                "123123",
                "pietro@confeitech.com",
                "11945200901",
                "2000-01-01",
                "03526020",
                true
        );

        EncomendaExibicaoDTO encomenda =
                new EncomendaExibicaoDTO(
                        2L,
                        15.0,
                        "Nenhuma observação",
                        cake,
                        "cenoura apimentada",
                        AndamentoEncomenda.AGUARDANDO,
                        LocalDate.now(),
                        LocalDate.parse("2025-10-17"),
                        user
                );

        when(EncomendaService.getEncomendas(3L)).thenReturn(user);

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

        when(EncomendaService.saveCake(Mockito.any(CakeDTO.class))).thenReturn(user);

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
        when(EncomendaService.saveCake(Mockito.any(CakeDTO.class)))
                .thenThrow(new ApplicationExceptionHandler("O peso do bolo é um campo obrigatório", HttpStatus.BAD_REQUEST));

        mockMvc.perform(MockMvcRequestBuilders.post("/cakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request  .andExpect(jsonPath("$.message").value("padrão de email incorreto"));  // Custom message for validation error

    }
}
