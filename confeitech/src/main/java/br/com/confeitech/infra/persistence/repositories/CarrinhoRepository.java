package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.models.CarrinhoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<CarrinhoModel, Long> {
}
