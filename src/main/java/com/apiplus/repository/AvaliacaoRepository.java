package com.apiplus.repository;

import com.apiplus.domain.Avaliacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Avaliacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {}
