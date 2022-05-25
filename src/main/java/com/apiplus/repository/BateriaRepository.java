package com.apiplus.repository;

import com.apiplus.domain.Bateria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Bateria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BateriaRepository extends JpaRepository<Bateria, Long> {}
