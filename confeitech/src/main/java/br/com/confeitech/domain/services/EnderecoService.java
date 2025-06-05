package br.com.confeitech.domain.services;


import br.com.confeitech.application.dtos.EnderecoDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.infra.persistence.mappers.EnderecoMapper;
import br.com.confeitech.infra.persistence.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import static br.com.confeitech.application.utils.MessageUtils.CEP_NOT_FOUND;
import static br.com.confeitech.application.utils.MessageUtils.EMAIL_EXISTS;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    private static final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    public void saveEndereco(String cep) {

        try {
            RestClient client = RestClient.builder()
                    .baseUrl("https://viacep.com.br/ws/")
                    .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                    .build();

            String raw = client.get()
                    .uri(cep + "/json")
                    .retrieve()
                    .body(String.class);

            System.out.println(raw);

            EnderecoDTO enderecoDTO = client.get()
                    .uri(cep + "/json")
                    .retrieve()
                    .body(EnderecoDTO.class);

            System.out.println(enderecoDTO.logradouro());

            enderecoRepository.save(enderecoMapper.enderecoDTOToEnderecoModel(enderecoDTO));

        }
        catch(Exception e) {
            throw new ApplicationExceptionHandler(CEP_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }




    }
}
