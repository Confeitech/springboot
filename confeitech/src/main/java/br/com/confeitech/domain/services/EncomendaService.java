package br.com.confeitech.domain.services;

import br.com.confeitech.application.dtos.AdicionalDTO;
import br.com.confeitech.application.dtos.AndamentoDTO;
import br.com.confeitech.application.dtos.CakeDTO;
import br.com.confeitech.application.dtos.EncomendaDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.application.utils.EscritorCSV;
import br.com.confeitech.domain.enums.AndamentoEncomenda;
import br.com.confeitech.domain.models.AdicionalModel;
import br.com.confeitech.domain.models.CakeModel;
import br.com.confeitech.domain.models.EncomendaModel;
import br.com.confeitech.infra.persistence.mappers.EncomendaMapper;
import br.com.confeitech.infra.persistence.repositories.EncomendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.confeitech.application.utils.MessageUtils.*;

@Service
public class EncomendaService {

    @Autowired
    private EncomendaMapper encomendaMapper;

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private CakeService cakeService;

    public List<EncomendaDTO> getEncomendas() {

        List<EncomendaModel> encomendas = encomendaRepository.findAll();

        if(encomendas.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDAS_NOT_FOUND, HttpStatus.NO_CONTENT);
        }

        return  encomendas
                .stream()
                .map(encomendaMapper::encomendaModelToEncomendaDTO)
                .collect(Collectors.toList());
    }

    public EncomendaDTO getEncomendaPerId(Long id) {

        Optional<EncomendaModel> encomenda = encomendaRepository.findById(id);

        if (encomenda.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDA_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return encomendaMapper.encomendaModelToEncomendaDTO(encomenda.get());
    }

    public List<EncomendaDTO> getEncomendasPorBolo(Long boloId) {

        List<EncomendaModel> encomendas = encomendaRepository.findByBoloId(boloId);

        if(encomendas.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDAS_BOLO_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return  encomendas
                .stream()
                .map(encomendaMapper::encomendaModelToEncomendaDTO)
                .collect(Collectors.toList());
    }

    public EncomendaDTO saveEncomenda(EncomendaDTO encomendaDTO) {

        EncomendaModel encomenda = encomendaMapper.encomendaDTOToEncomendaModel(encomendaDTO);
        encomenda.setBolo(cakeService.getCakePerId(encomendaDTO.bolo()));
        encomenda.setAndamento(AndamentoEncomenda.AGUARDANDO);
        encomenda.setData(LocalDate.now());

        return encomendaMapper.encomendaModelToEncomendaDTO(encomendaRepository.save(encomenda));
    }

    public EncomendaDTO alterarAndamentoDaEncomenda(AndamentoDTO andamentoDTO, Long id) {

        Optional<EncomendaModel> encomenda = encomendaRepository.findById(id);

        if (encomenda.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDA_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        EncomendaModel encomendaModel = encomenda.get();

        encomendaModel.setAndamento(andamentoDTO.andamentoEncomenda());
        return encomendaMapper.encomendaModelToEncomendaDTO(encomendaRepository.save(encomendaModel));
    }

    public Set<EncomendaModel> cadastraEncomendasAntes(List<EncomendaDTO> encomendaDTOS) {

        Set<EncomendaModel> encomendas = new HashSet<>();

        for(EncomendaDTO encomendaDTO : encomendaDTOS) {

            System.out.println("estou passando aqui");

            encomendas.add(encomendaMapper.encomendaDTOToEncomendaModel(saveEncomenda(encomendaDTO)));
        }

        return encomendas;
    }

    public void gerarCSV() {

        List<EncomendaDTO> encomendas = getEncomendas();

        EscritorCSV.Escrever(pegarBoloMaisVendido(), encomendas.size());


    }

    public String pegarBoloMaisVendido() {

        return encomendaRepository.findMostFrequentNome().get();
    }

}
