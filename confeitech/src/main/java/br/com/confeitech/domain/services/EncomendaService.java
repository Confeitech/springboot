package br.com.confeitech.domain.services;

import br.com.confeitech.application.dtos.AndamentoDTO;
import br.com.confeitech.application.dtos.EncomendaDTO;
import br.com.confeitech.application.dtos.EncomendaExibicaoDTO;
import br.com.confeitech.application.dtos.UserDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.application.utils.EscritorCSV;
import br.com.confeitech.domain.enums.AndamentoEncomenda;
import br.com.confeitech.domain.models.EncomendaModel;
import br.com.confeitech.infra.persistence.mappers.CakeMapper;
import br.com.confeitech.infra.persistence.mappers.EncomendaMapper;
import br.com.confeitech.infra.persistence.mappers.UserMapper;
import br.com.confeitech.infra.persistence.repositories.EncomendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.confeitech.application.utils.MessageUtils.*;

@Service
public class EncomendaService {

    @Autowired
    private EncomendaMapper encomendaMapper;

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CakeMapper cakeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CakeService cakeService;



    public List<EncomendaExibicaoDTO> getEncomendas() {

        List<EncomendaModel> encomendas = encomendaRepository.findAll();

        if(encomendas.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDAS_NOT_FOUND, HttpStatus.NO_CONTENT);
        }

        List<EncomendaExibicaoDTO> encomendasExibicao = new ArrayList<>();

        for (EncomendaModel encomenda : encomendas) {
            encomendasExibicao.add(new EncomendaExibicaoDTO(encomenda));
        }

        return encomendasExibicao;
    }

    public List<EncomendaExibicaoDTO> getEncomendaPorUsuario(Long id) {

        List<EncomendaModel> encomendas = encomendaRepository.findByUserId(id);

        if (encomendas.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDAS_NOT_FOUND, HttpStatus.NO_CONTENT);
        }

        List<EncomendaExibicaoDTO> encomendasExibicao = new ArrayList<>();

        for (EncomendaModel encomenda : encomendas) {
            encomendasExibicao.add(new EncomendaExibicaoDTO(encomenda));
        }


        return encomendasExibicao;
    }

    public EncomendaExibicaoDTO getEncomendaPerId(Long id) {

        Optional<EncomendaModel> encomenda = encomendaRepository.findById(id);

        if (encomenda.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDA_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return new EncomendaExibicaoDTO(encomenda.get());

    }

    public List<EncomendaExibicaoDTO> getEncomendasPorBolo(Long boloId) {

        List<EncomendaModel> encomendas = encomendaRepository.findByBoloId(boloId);

        if(encomendas.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDAS_BOLO_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        List<EncomendaExibicaoDTO> encomendasExibicao = new ArrayList<>();

        for (EncomendaModel encomenda : encomendas) {
            encomendasExibicao.add(new EncomendaExibicaoDTO(encomenda));
        }

        return encomendasExibicao;
    }

    public EncomendaExibicaoDTO saveEncomenda(EncomendaDTO encomendaDTO) {

        EncomendaModel encomenda = encomendaMapper.encomendaDTOToEncomendaModel(encomendaDTO);
        encomenda.setBolo(cakeService.getCakePerId(encomendaDTO.bolo()));
        encomenda.setAndamento(AndamentoEncomenda.AGUARDANDO);
        encomenda.setUser(userService.getUserPerId(encomendaDTO.user()));
        encomenda.setDataCriacao(LocalDate.now());
        encomenda.setDataRetirada(encomendaDTO.dataRetirada());

        return new EncomendaExibicaoDTO(encomendaRepository.save(encomenda));
    }

    public EncomendaExibicaoDTO alterarAndamentoDaEncomenda(AndamentoDTO andamentoDTO, Long id) {

        Optional<EncomendaModel> encomenda = encomendaRepository.findById(id);

        if (encomenda.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDA_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        EncomendaModel encomendaModel = encomenda.get();

        encomendaModel.setAndamento(andamentoDTO.andamentoEncomenda());

        return new EncomendaExibicaoDTO(encomendaRepository.save(encomendaModel));
    }

//    public Set<EncomendaModel> cadastraEncomendasAntes(List<EncomendaDTO> encomendaDTOS) {
//
//        Set<EncomendaModel> encomendas = new HashSet<>();
//
//        for(EncomendaDTO encomendaDTO : encomendaDTOS) {
//
//            System.out.println("estou passando aqui");
//
//            EncomendaExibicaoDTO encomendaSalva = saveEncomenda(encomendaDTO);
//            EncomendaModel encomendaRetorno = new EncomendaModel(
//                    encomendaSalva.id(),
//                    encomendaSalva.preco(),
//                    encomendaSalva.observacoes(),
//                    encomendaSalva.preco(),
//                    cakeMapper.cakeDTOToCakeModel(encomendaSalva.bolo()),
//                    encomendaSalva.adicionais(),
//                    encomendaSalva.andamento(),
//                    encomendaSalva.dataCriacao(),
//                    encomendaSalva.dataRetirada(),
//                    null,
//                    userMapper.userDTOToUserModel(encomendaSalva.userDTO())
//
//            );
//
//            encomendas.add(encomendaRetorno);
//        }
//
//        return encomendas;
//    }

    public List<EncomendaExibicaoDTO> getEncomendasAceitas() {

        List<EncomendaModel> encomendas = encomendaRepository.findByAndamento(AndamentoEncomenda.EM_PREPARO);


        if(encomendas.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDAS_NOT_FOUND, HttpStatus.NO_CONTENT);
        }


        List<EncomendaExibicaoDTO> encomendasExibicao = new ArrayList<>();

        for (EncomendaModel encomenda : encomendas) {
            encomendasExibicao.add(new EncomendaExibicaoDTO(encomenda));
        }

        return encomendasExibicao;
    }

    public List<EncomendaExibicaoDTO> getEncomendasEmAguardo() {

        List<EncomendaModel> encomendas = encomendaRepository.findByAndamento(AndamentoEncomenda.AGUARDANDO);


        if(encomendas.isEmpty()) {
            throw new ApplicationExceptionHandler(ENCOMENDAS_NOT_FOUND, HttpStatus.NO_CONTENT);
        }

        List<EncomendaExibicaoDTO> encomendasExibicao = new ArrayList<>();

        for (EncomendaModel encomenda : encomendas) {
            encomendasExibicao.add(new EncomendaExibicaoDTO(encomenda));
        }


        return encomendasExibicao;
    }


    public void gerarCSV() {
//
//        List<EncomendaExibicaoDTO> encomendas = getEncomendas();
//
//        EscritorCSV.Escrever(pegarBoloMaisVendido(), encomendas.size());

    }

    public String pegarBoloMaisVendido() {

        return encomendaRepository.findMostFrequentNome().get();
    }

}
