package com.apiplus.repository;

import com.apiplus.domain.Kit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Kit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KitRepository extends JpaRepository<Kit, Long> {}
