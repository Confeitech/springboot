package br.com.confeitech.domain.services;

import br.com.confeitech.application.dtos.AdicionalDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.domain.models.AdicionalModel;
import br.com.confeitech.infra.persistence.mappers.AdicionalMapper;
import br.com.confeitech.infra.persistence.repositories.AdicionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static br.com.confeitech.application.utils.MessageUtils.*;
import static br.com.confeitech.application.utils.MessageUtils.INACTIVE_ADICIONAL;

@Service
public class AdicionalService {

    @Autowired
    private AdicionalRepository adicionalRepository;

    @Autowired
    private AdicionalMapper adicionalMapper;

    public List<AdicionalDTO> getAdicionais() {

        List<AdicionalModel> adicionais = adicionalRepository.findByActive(true);

        if(adicionais.isEmpty()) {
            throw new ApplicationExceptionHandler(ADICIONAIS_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return  adicionais
                .stream()
                .map(adicionalMapper::AdicionalModelToAdicionalDTO)
                .collect(Collectors.toList());
    }

    public AdicionalDTO getAdicionalPorId(Long id) {

        Optional<AdicionalModel> adicional = adicionalRepository.findById(id);

        if (adicional.isEmpty()) {
            throw new ApplicationExceptionHandler(ADICIONAL_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return adicionalMapper.AdicionalModelToAdicionalDTO(adicional.get());
    }

    public AdicionalDTO saveAdicionais(AdicionalDTO adicionaisDTO) {

        AdicionalModel adicionaisModel = adicionalMapper.adicionalDTOToAdicionalModel(adicionaisDTO);
        adicionaisModel.setActive(true);
        adicionalRepository.save(adicionaisModel);

        return adicionalMapper.AdicionalModelToAdicionalDTO(adicionaisModel);

    }

    public AdicionalDTO updateAdicional(Long id, AdicionalDTO adicionalDTO) {

        AdicionalModel adicional = adicionalMapper.adicionalDTOToAdicionalModel(getAdicionalPorId(id));

        adicional.setNome(adicionalDTO.nome());
        adicional.setPreco(adicionalDTO.preco());
        adicional.setActive(adicionalDTO.active());

        adicionalRepository.save(adicional);

        return adicionalMapper.AdicionalModelToAdicionalDTO(adicional);
    }

    public void deleteAdicional(Long id) {

        AdicionalModel adicional = adicionalMapper.adicionalDTOToAdicionalModel(getAdicionalPorId(id));

        if (!adicional.getActive()) {
            throw new ApplicationExceptionHandler(INACTIVE_ADICIONAL, HttpStatus.BAD_REQUEST);
        }

        adicional.setActive(false);

        adicionalRepository.save(adicional);
    }

    //Métodos próprios

    public Set<AdicionalModel> mapearAdicionaisDTOParaModel(List<AdicionalDTO> adicionaisDTO) {

        Set<AdicionalModel> adicionais = new HashSet<>();

        for(AdicionalDTO adicionalDTO : adicionaisDTO) {
            adicionais.add(adicionalMapper.adicionalDTOToAdicionalModel(adicionalDTO));
        }

        return adicionais;
    }

    public Set<AdicionalDTO> mapearAdicionaisModelParaDTO(List<AdicionalModel> adicionais) {

        Set<AdicionalDTO> adicionaisDTO = new HashSet<>();

        for(AdicionalModel adicionalModel : adicionais) {
            adicionaisDTO.add(adicionalMapper.AdicionalModelToAdicionalDTO(adicionalModel));
        }

        return adicionaisDTO;
    }

    public void cadastraAdicionalAntesDeMapear(List<AdicionalDTO> adicionaisDTOS) {

        for(AdicionalDTO adicionalDTO : adicionaisDTOS) {
            saveAdicionais(adicionalDTO);
        }

    }

//    public Set<AdicionalModel> retornarListaParaMapeamento(List<AdicionalDTO> adicionaisDTOS) {
//
//        Set<AdicionalModel> adicionais = new HashSet<>();
//        AdicionalModel adicionalModel = new AdicionalModel();
//
//        for(AdicionalDTO adicionalDTO : adicionaisDTOS) {
//
//            Optional<AdicionalModel> adicional = adicionalRepository.findByNome(adicionalDTO.nome());
//
//            adicionalModel = adicional.orElseGet(() -> adicionalMapper.adicionalDTOToAdicionalModel(saveAdicionais(adicionalDTO)));
//
//
//
//            adicionais.add(adicionalModel);
//        }
//
//        return adicionais;
//    }

    public Set<AdicionalModel> retornarListaParaMapeamento(List<AdicionalDTO> adicionaisDTOS) {
        return retornarListaRecursiva(adicionaisDTOS, 0, new HashSet<>());
    }

    private Set<AdicionalModel> retornarListaRecursiva(List<AdicionalDTO> adicionaisDTOS, int index, Set<AdicionalModel> adicionais) {
        // Caso base: se o índice alcançar o final da lista, retorna o conjunto acumulado
        if (index >= adicionaisDTOS.size()) {
            return adicionais;
        }

        AdicionalDTO adicionalDTO = adicionaisDTOS.get(index);

        // Busca o AdicionalModel correspondente ao AdicionalDTO atual
        Optional<AdicionalModel> adicional = adicionalRepository.findByNome(adicionalDTO.nome());

        // Mapeia o adicionalDTO para AdicionalModel, se necessário
        AdicionalModel adicionalModel = adicional.orElseGet(() -> adicionalMapper.adicionalDTOToAdicionalModel(saveAdicionais(adicionalDTO)));

        // Adiciona ao conjunto
        adicionais.add(adicionalModel);

        // Chama recursivamente para o próximo índice
        return retornarListaRecursiva(adicionaisDTOS, index + 1, adicionais);
    }

}