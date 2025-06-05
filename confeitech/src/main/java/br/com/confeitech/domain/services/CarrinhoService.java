package br.com.confeitech.domain.services;

import br.com.confeitech.application.dtos.CarrinhoDTO;
import br.com.confeitech.application.dtos.EncomendaDTO;
import br.com.confeitech.application.exceptions.ApplicationExceptionHandler;
import br.com.confeitech.domain.models.CarrinhoModel;
import br.com.confeitech.domain.models.EncomendaModel;
import br.com.confeitech.infra.persistence.mappers.CarrinhoMapper;
import br.com.confeitech.infra.persistence.repositories.CarrinhoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.confeitech.application.utils.MessageUtils.*;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoMapper carrinhoMapper;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private EncomendaService encomendaService;



    public List<CarrinhoDTO> getCarrinhos() {

        List<CarrinhoModel> carrinhos = carrinhoRepository.findAll();

        if(carrinhos.isEmpty()) {
            throw new ApplicationExceptionHandler(CARRINHOS_NOT_FOUND, HttpStatus.NO_CONTENT);
        }

        return  carrinhos
                .stream()
                .map(carrinhoMapper::carrinhoModelToCarrinhoDTO)
                .collect(Collectors.toList());
    }

    public CarrinhoDTO getCarrinhoPorId(Long id) {

        Optional<CarrinhoModel> carrinho = carrinhoRepository.findById(id);

        if (carrinho.isEmpty()) {
            throw new ApplicationExceptionHandler(CARRINHO_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return carrinhoMapper.carrinhoModelToCarrinhoDTO(carrinho.get());
    }


    public CarrinhoDTO saveCarrinho(CarrinhoDTO carrinhoDTO) {

        CarrinhoModel carrinho = carrinhoMapper.carrinhoDTOToCarrinhoModel(carrinhoDTO);
        carrinho.setEncomendas(encomendaService.cadastraEncomendasAntes(carrinhoDTO.encomendas()));
        carrinho.setDataCompra(LocalDate.now());
        carrinho.setDataRetirada(LocalDateTime.from(carrinhoDTO.dataRetirada()));

        return carrinhoMapper.carrinhoModelToCarrinhoDTO(carrinhoRepository.save(carrinho));

    }

}
