package br.com.confeitech.infra.persistence.repositories;

import br.com.confeitech.domain.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long>  {
    Boolean existsByEmail(String email);

    Optional<UserModel> findByIdAndActive(Long id, Boolean active);
}
