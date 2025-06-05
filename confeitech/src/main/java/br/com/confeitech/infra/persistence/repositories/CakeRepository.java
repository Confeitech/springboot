package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.models.CakeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CakeRepository extends JpaRepository<CakeModel, Long> {

    List<CakeModel> findByActive(Boolean active);
    Optional<CakeModel> findByIdAndActive(Long id, Boolean active);

    @Modifying
    @Transactional
    @Query("update CakeModel b set b.imagem = ?2 where b.id = ?1")
    void setImagem(Long id, byte[] foto);

    @Query("select b.imagem from CakeModel b where b.id = ?1")
    byte[] getImagem(Long id);
}
