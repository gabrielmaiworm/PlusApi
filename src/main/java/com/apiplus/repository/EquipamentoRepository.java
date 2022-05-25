package com.apiplus.repository;

import com.apiplus.domain.Equipamento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Equipamento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {}
