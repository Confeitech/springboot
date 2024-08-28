package br.com.confeitech.repository;

import br.com.confeitech.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long>  {

    Boolean existsByEmail(String email);
}
