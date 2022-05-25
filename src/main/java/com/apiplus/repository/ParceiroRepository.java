package com.apiplus.repository;

import com.apiplus.domain.Parceiro;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Parceiro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParceiroRepository extends JpaRepository<Parceiro, Long> {
    @Query("select parceiro from Parceiro parceiro where parceiro.user.login = ?#{principal.username}")
    List<Parceiro> findByUserIsCurrentUser();
}
