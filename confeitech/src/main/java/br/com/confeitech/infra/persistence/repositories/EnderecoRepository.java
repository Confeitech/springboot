package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Integer> {
}
