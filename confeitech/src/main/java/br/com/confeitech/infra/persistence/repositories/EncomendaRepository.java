package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.models.EncomendaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EncomendaRepository extends JpaRepository<EncomendaModel, Long> {

    List<EncomendaModel> findByBoloId(Long boloId);
}
