package br.com.confeitech.application.dtos;

import br.com.confeitech.domain.models.CarrinhoModel;
import br.com.confeitech.domain.models.EncomendaModel;
import br.com.confeitech.domain.models.UserModel;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CarrinhoDTO(

        Long id,
        @NotNull(message = "O preço total é um campo obrigatório!")
        Double precoTotal,
        LocalDate dataCompra,
        @NotNull(message = "A data da retirada é um campo obrigatório!")
        LocalDateTime dataRetirada,
        @NotNull(message = "Um carrinho não pode conter nenhuma encomenda!")
        List<EncomendaDTO> encomendas

) {

//        public CarrinhoDTO(CarrinhoModel carrinhoModel) {
//                this(
//                        userModel.getName(),
//                        userModel.getEmail(),
//                        userModel.getPassword(),
//                        userModel.getPhone(),
//                        userModel.getBirthDate() != null ? userModel.getBirthDate().toString() : null,
//                        null,
//                        userModel.getActive()
//                );
//        }
}
