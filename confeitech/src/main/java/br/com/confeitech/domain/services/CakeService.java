package br.com.confeitech.domain.services;

import br.com.confeitech.application.dtos.AdicionalDTO;
import br.com.confeitech.application.dtos.CakeDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.application.utils.Lista;
import br.com.confeitech.application.utils.Ordenacao;
import br.com.confeitech.domain.models.AdicionalModel;
import br.com.confeitech.domain.models.CakeModel;
import br.com.confeitech.infra.persistence.mappers.CakeMapper;
import br.com.confeitech.infra.persistence.repositories.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.confeitech.application.utils.MessageUtils.*;

@Service
public class CakeService implements Ordenacao<CakeModel> {

    @Autowired
    private CakeRepository cakeRepository;

    @Autowired
    private CakeMapper cakeMapper;

    @Autowired
    private AdicionalService adicionalService;

    Stack<Long> pilha = new Stack<>();

    public List<CakeDTO> getCakes() {

        List<CakeModel> cakes = cakeRepository.findByActive(true);

        if(cakes.isEmpty()) {
            throw new ApplicationExceptionHandler(CAKES_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return  cakes
                .stream()
                .map(cakeMapper::cakeModelToCakeDTO)
                .collect(Collectors.toList());
    }


    public List<CakeDTO> getCakesByAlfabeticalOrder() {

        List<CakeModel> cakes = cakeRepository.findAll();

        if(cakes.isEmpty()) {
            throw new ApplicationExceptionHandler(CAKES_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        cakes = ordenarListaEmOrdemAlfabetica(cakes, 0 , cakes.size() -1);

        return  cakes
                .stream()
                .map(cakeMapper::cakeModelToCakeDTO)
                .collect(Collectors.toList());
    }

    public CakeDTO getCake(Long id) {

        Optional<CakeModel> cake = cakeRepository.findByIdAndActive(id, true);

        if (cake.isEmpty()) {
            throw new ApplicationExceptionHandler(CAKE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return cakeMapper.cakeModelToCakeDTO(cake.get());
    }


    public CakeDTO saveCake(CakeDTO cakeDTO) {

        CakeModel cake = cakeMapper.cakeDTOToCakeModel(cakeDTO);
        cake.setAdicionais(adicionalService.retornarListaParaMapeamento(cakeDTO.adicionais()));
        cake.setImage(cakeDTO.image());
        cakeRepository.save(cake);
        return cakeMapper.cakeModelToCakeDTO(cake);
    }


    public CakeDTO updateCake(Long id, CakeDTO cakeDTO) {

        CakeModel cake = getCakePerId(id);

        cake.setName(cakeDTO.name());
        cake.setPrice(cakeDTO.price());
        cake.setDescription(cakeDTO.description());
        cake.setActive(cakeDTO.active());
        cake.setAdicionais(adicionalService.retornarListaParaMapeamento(cakeDTO.adicionais()));

        cakeRepository.save(cake);

        return cakeMapper.cakeModelToCakeDTO(cake);
    }

    public void deleteCake(Long id) {

        CakeModel cake = getCakePerId(id);

        if (!cake.getActive()) {
            throw new ApplicationExceptionHandler(INACTIVE_CAKE, HttpStatus.BAD_REQUEST);
        }

        cake.setActive(false);

        pilha.push(cake.getId());

        cakeRepository.save(cake);
    }


    public CakeModel getCakePerId(Long id) {
        Optional<CakeModel> optionalCake = cakeRepository.findById(id);

        if (optionalCake.isEmpty()) {
            throw new ApplicationExceptionHandler(CAKE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return optionalCake.get();
    }

    public void desfazerDelecao() {

        if(pilha.isEmpty()) {
        throw new ApplicationExceptionHandler(NO_CAKE_TO_DELETE, HttpStatus.NOT_FOUND);
        }

        CakeModel bolo = getCakePerId(pilha.pop());
        bolo.setActive(true);
        cakeRepository.save(bolo);
    }

    //métodos próprios (além do crud)


    //ordena uma lista de bolos em ordem alfabética
    @Override
    public List<CakeModel> ordenarListaEmOrdemAlfabetica(List<CakeModel> listaDesordenada, int indInicio, int indFim) {


        int i = indInicio, j = indFim;

        // Pega o bolo no meio da lista
        CakeModel pivo = listaDesordenada.get((indInicio + indFim) / 2);

        while (i <= j) {
            // Incrementa i até encontrar um valor maior ou igual ao pivô
            while (i < indFim && listaDesordenada.get(i).getName().compareTo(pivo.getName()) < 0) {
                i++;
            }

            // Decrementa j até encontrar um valor menor ou igual ao pivô
            while (j > indInicio && listaDesordenada.get(j).getName().compareTo(pivo.getName()) > 0) {
                j--;
            }

            // Troca os elementos se necessário
            if (i <= j) {
                Collections.swap(listaDesordenada, i, j);
                i++;
                j--;
            }
        }

        if (indInicio < j) {
            ordenarListaEmOrdemAlfabetica(listaDesordenada, indInicio, j);
        }

        if (i < indFim) {
            ordenarListaEmOrdemAlfabetica(listaDesordenada, i, indFim);
        }

        return listaDesordenada;
    }

    //busca por um bolo pelo seu nome
    public CakeDTO buscarBoloPorNome(String nome) {

        List<CakeDTO> bolos = getCakesByAlfabeticalOrder();

        int indiceMenor = 0;
        int indiceMaior = bolos.size() - 1;

        while (indiceMenor <= indiceMaior) {
            int mid = indiceMenor + (indiceMaior - indiceMenor) / 2;

            /**
             *  * Compara a string de entrada com o nome do bolo localizado no meio da lista.
             *  * O método remove espaços em branco ao redor da string e transforma ambas as strings para
             *  * minúsculas, garantindo uma comparação insensível a maiúsculas/minúsculas e espaços.
             */
            int result = nome.trim()
                    .toLowerCase()
                    .compareTo(bolos.get(mid)
                            .name()
                            .trim()
                            .toLowerCase());

            if (result == 0) {
                return bolos.get(mid);
            }

            if (result > 0) {
                indiceMenor = mid + 1;
            }

            else {
                indiceMaior = mid - 1;
            }
        }

        throw new ApplicationExceptionHandler(CAKE_NOT_FOUND, HttpStatus.NOT_FOUND);

    }

}
