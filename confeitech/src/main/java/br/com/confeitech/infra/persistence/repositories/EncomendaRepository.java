package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.enums.AndamentoEncomenda;
import br.com.confeitech.domain.models.EncomendaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EncomendaRepository extends JpaRepository<EncomendaModel, Long> {

    List<EncomendaModel> findByBoloId(Long boloId);

    List<EncomendaModel> findByAndamento(AndamentoEncomenda andamento);

//    @Query("SELECT COUNT(e) FROM Encomenda e WHERE e.andamento = 2")
//    Long countByAndamentoConcluido();

    @Query(value = """
    SELECT COUNT(*)\s
    FROM encomenda\s
    WHERE data_criacao >= :dataInicio\s
      AND andamento = 'PRONTA'
    """, nativeQuery = true)
    Long qtdEncomendasConcluidasUltimoMes(@Param("dataInicio") LocalDate dataInicio);


    @Query(value = """
    SELECT COUNT(*)\s
    FROM encomenda\s
    WHERE data_criacao >= :dataInicio
    """, nativeQuery = true)
    Long qtdEncomendasFeitasUltimoMes(@Param("dataInicio") LocalDate dataInicio);

    @Query(value = """
        SELECT DAYNAME(data_criacao) AS diaSemana
        FROM encomenda
        GROUP BY diaSemana
        ORDER BY COUNT(*) DESC
        LIMIT 1
        """, nativeQuery = true)
    String diaDeEncomendaMaisFrequenteSemana();


//    @Query("SELECT MONTH(e.dataCriacao) - 1 AS month, COUNT(e) FROM EncomendaModel e " +
//            "WHERE e.dataCriacao BETWEEN :startDate AND :endDate " +
//            "GROUP BY MONTH(e.dataCriacao) " +
//            "ORDER BY month ASC")
//    List<Object[]> graficoMes(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//



    @Query(value = """
        SELECT bolo_id
        FROM encomenda
        GROUP BY bolo_id
        ORDER BY COUNT(bolo_id) DESC
        LIMIT 1
        """, nativeQuery = true)
    Long boloMaisComprado();

    @Query(value = "SELECT b.nome " +
            "FROM encomenda e " +
            "JOIN bolo b ON e.bolo_id = b.id " +
            "GROUP BY b.id, b.nome " +
            "ORDER BY COUNT(e.bolo_id) DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<String> findMostFrequentNome();

    List<EncomendaModel> findByUserId(Long userId);
}
