package com.apiplus.repository;

import com.apiplus.domain.InfoAdicional;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InfoAdicional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfoAdicionalRepository extends JpaRepository<InfoAdicional, Long> {
    @Query("select infoAdicional from InfoAdicional infoAdicional where infoAdicional.user.login = ?#{principal.username}")
    List<InfoAdicional> findByUserIsCurrentUser();
}
