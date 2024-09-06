package br.com.confeitech.domain.services;

import br.com.confeitech.application.dtos.CakeDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.application.utils.Ordenacao;
import br.com.confeitech.domain.models.CakeModel;
import br.com.confeitech.infra.persistence.mappers.CakeMapper;
import br.com.confeitech.infra.persistence.repositories.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.confeitech.application.utils.MessageUtils.*;

@Service
public class CakeService implements Ordenacao<CakeModel> {

    @Autowired
    private CakeRepository cakeRepository;

    @Autowired
    private CakeMapper cakeMapper;


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

        cakes = ordenarListaEmOrdemAlfabetica(cakes);

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
        cakeRepository.save(cake);

        return cakeMapper.cakeModelToCakeDTO(cake);
    }


    public CakeDTO updateCake(Long id, CakeDTO cakeDTO) {

        CakeModel cake = getCakePerId(id);

        cake.setName(cakeDTO.name());
        cake.setPrice(cakeDTO.price());
        cake.setWeight(cakeDTO.weight());
        cake.setDescription(cakeDTO.description());
        cake.setContainsGluten(cakeDTO.containsGluten());
        cake.setContainsLactose(cakeDTO.containsLactose());
        cake.setActive(cakeDTO.active());

        cakeRepository.save(cake);

        return cakeMapper.cakeModelToCakeDTO(cake);
    }

    public void deleteCake(Long id) {

        CakeModel cake = getCakePerId(id);

        if (!cake.getActive()) {
            throw new ApplicationExceptionHandler(INACTIVE_CAKE, HttpStatus.BAD_REQUEST);
        }

        cake.setActive(false);

        cakeRepository.save(cake);
    }


    public CakeModel getCakePerId(Long id) {
        Optional<CakeModel> optionalCake = cakeRepository.findById(id);

        if (optionalCake.isEmpty()) {
            throw new ApplicationExceptionHandler(CAKE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return optionalCake.get();
    }


    @Override
    public List<CakeModel> ordenarListaEmOrdemAlfabetica(List<CakeModel> listaDesordenada) {

        List<CakeModel> listaOrdenada = listaDesordenada.stream()
                .sorted(Comparator.comparing(CakeModel::getName))
                .collect(Collectors.toList());

        return listaOrdenada;
    }
}
