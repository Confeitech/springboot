package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.enums.AndamentoEncomenda;
import br.com.confeitech.domain.models.EncomendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EncomendaRepository extends JpaRepository<EncomendaModel, Long> {

    List<EncomendaModel> findByBoloId(Long boloId);

    List<EncomendaModel> findByAndamento(AndamentoEncomenda andamento);

    @Query(value = "SELECT b.nome " +
            "FROM encomenda e " +
            "JOIN bolo b ON e.bolo_id = b.id " +
            "GROUP BY b.id, b.nome " +
            "ORDER BY COUNT(e.bolo_id) DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<String> findMostFrequentNome();
}
