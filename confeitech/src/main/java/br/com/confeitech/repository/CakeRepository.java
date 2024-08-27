package br.com.confeitech.repository;

import br.com.confeitech.models.CakeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CakeRepository extends JpaRepository<CakeModel, Long> {
}
