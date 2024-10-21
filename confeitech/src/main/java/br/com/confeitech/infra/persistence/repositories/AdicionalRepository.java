package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.models.AdicionalModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdicionalRepository extends JpaRepository<AdicionalModel, Long> {

    List<AdicionalModel> findByActive(Boolean active);

    Optional<AdicionalModel> findByNome(String nome);
}
