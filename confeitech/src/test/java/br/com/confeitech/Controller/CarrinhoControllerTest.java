package br.com.confeitech.Controller;

import br.com.confeitech.application.dtos.*;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.domain.enums.AndamentoEncomenda;
import br.com.confeitech.domain.services.CarrinhoService;
import br.com.confeitech.infra.controllers.CarrinhoController;
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
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarrinhoController.class)
public class CarrinhoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarrinhoService CarrinhoService;

    @Mock
    private CarrinhoController CarrinhoController;

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
    @DisplayName("Verifica o primeiro carrinho e retorna 200")
    void getAllUsersTest() throws Exception {

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

        List<EncomendaDTO> encomendas = List.of(
                new EncomendaDTO(
                        1L,
                        10.0,
                        "Sem pimenta por favor",
                        2.0,
                        2L,
                        "morango e chocolate",
                        AndamentoEncomenda.AGUARDANDO,
                        LocalDate.now(),
                        LocalDate.parse("2025-10-17"),
                        2L
                ),
                new EncomendaDTO(
                        2L,
                        15.0,
                        "Nenhuma observação",
                        2.0,
                        1L,
                        "cenoura apimentada",
                        AndamentoEncomenda.AGUARDANDO,
                        LocalDate.now(),
                        LocalDate.parse("2025-10-17"),
                        1L
                )
        );

        List<CarrinhoDTO> carrinhos = List.of(
                new CarrinhoDTO(
                        1L,
                        10.2,
                        LocalDate.now(),
                        LocalDateTime.parse("2025-10-17T14:30:45.123"),
                        encomendas
                ),
                new CarrinhoDTO(
                        2L,
                        21.0,
                        LocalDate.now(),
                        LocalDateTime.parse("2025-10-17T14:30:45.123"),
                        encomendas
                )
        );


        when(CarrinhoService.getCarrinhos()).thenReturn(carrinhos);

        mockMvc.perform(get("/carrinhos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].precoTotal").value(10.2))
                .andExpect(jsonPath("$[0].dataCompra").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[0].dataRetirada").value(String.valueOf(LocalDateTime.parse("2025-10-17T14:30:45.123"))));
    }

    @Test
    @DisplayName("Dado que tenho um carrinho para salvar, retorna status 200")
    void saveCarrinhoTest() throws Exception {

        String json = """
                {
                	"precoTotal": 34.99,
                	"dataRetirada": "2025-10-17T14:30:45.123",
                	"encomendas": [
                		{
                			"preco": 15.0,
                			"observacoes": "oioi eu sou uma observação",
                			"bolo": 1,
                			"adicionais": "morango chocolate"
                		},
                				{
                			"preco": 15.0,
                			"observacoes": "oioi eu sou uma observação",
                			"bolo": 1,
                			"adicionais": "morango chocolate"
                		},
                				{
                			"preco": 15.0,
                			"observacoes": "oioi eu sou uma observação",
                			"bolo": 1,
                			"adicionais": "morango chocolate"
                		}
                	]
                }
                """;

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

        List<EncomendaDTO> encomendas = List.of(
                new EncomendaDTO(
                        1L,
                        10.0,
                        "Sem pimenta por favor",
                        2.0,
                        2L,
                        "morango e chocolate",
                        AndamentoEncomenda.AGUARDANDO,
                        LocalDate.now(),
                        LocalDate.parse("2025-10-17"),
                        2L
                ),
                new EncomendaDTO(
                        2L,
                        15.0,
                        "Nenhuma observação",
                        2.0,
                        1L,
                        "cenoura apimentada",
                        AndamentoEncomenda.AGUARDANDO,
                        LocalDate.now(),
                        LocalDate.parse("2025-10-17"),
                        1L
                )
        );


        CarrinhoDTO carrinho = new CarrinhoDTO(
                1L,
                10.2,
                LocalDate.now(),
                LocalDateTime.parse("2025-10-17T14:30:45.123"),
                encomendas
        );

        when(CarrinhoService.saveCarrinho(Mockito.any(CarrinhoDTO.class))).thenReturn(carrinho);

        mockMvc.perform(post("/carrinhos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.precoTotal").value(10.2))
                .andExpect(jsonPath("$.dataCompra").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.dataRetirada").value(String.valueOf(LocalDateTime.parse("2025-10-17T14:30:45.123"))));
    }

    @Test
    @DisplayName("Dado que a requisição contém dados inválidos, retorna status 400")
    void saveCarrinhoWithInvalidData() throws Exception {

        // Invalid JSON: invalid email format
        String json = """
                {
                    	"dataRetirada": "2025-10-17T14:30:45.123",
                    	"encomendas": [
                    		{
                    			"preco": 15.0,
                    			"observacoes": "oioi eu sou uma observação",
                    			"bolo": 1,
                    			"adicionais": "morango chocolate"
                    		},
                    				{
                    			"preco": 15.0,
                    			"observacoes": "oioi eu sou uma observação",
                    			"bolo": 1,
                    			"adicionais": "morango chocolate"
                    		},
                    				{
                    			"preco": 15.0,
                    			"observacoes": "oioi eu sou uma observação",
                    			"bolo": 1,
                    			"adicionais": "morango chocolate"
                    		}
                    	]
                    }
            """;

        // Mocking an invalid email scenario
        when(CarrinhoService.saveCarrinho(Mockito.any(CarrinhoDTO.class)))
                .thenThrow(new ApplicationExceptionHandler("preço é um campo obrigatório", HttpStatus.BAD_REQUEST));

        mockMvc.perform(post("/carrinhos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());  // Expect a 400 Bad Request  .andExpect(jsonPath("$.message").value("padrão de email incorreto"));  // Custom message for validation error
    }

}
