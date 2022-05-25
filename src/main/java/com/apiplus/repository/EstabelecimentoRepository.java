package com.apiplus.repository;

import com.apiplus.domain.Estabelecimento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Estabelecimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {}
