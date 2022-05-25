package com.apiplus.repository;

import com.apiplus.domain.ContaBancaria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContaBancaria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, Long> {}
