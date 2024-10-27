package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.models.CakeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CakeRepository extends JpaRepository<CakeModel, Long> {

    List<CakeModel> findByActive(Boolean active);
    Optional<CakeModel> findByIdAndActive(Long id, Boolean active);

}
