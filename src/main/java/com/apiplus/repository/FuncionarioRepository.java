package com.apiplus.repository;

import com.apiplus.domain.Funcionario;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Funcionario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    @Query("select funcionario from Funcionario funcionario where funcionario.user.login = ?#{principal.username}")
    List<Funcionario> findByUserIsCurrentUser();
}
