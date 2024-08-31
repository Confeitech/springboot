package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.models.CakeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CakeRepository extends JpaRepository<CakeModel, Long> {

    Optional<CakeModel> findByIdAndActive(Long id, Boolean active);
}
