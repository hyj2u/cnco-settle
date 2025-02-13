package com.example.cco.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AutoTotalRepository extends JpaRepository<AutoTotalEntity, String> {

    List<AutoTotalEntity> findAllByOrderByBrandCdAscSettlementCdAscStoreNameAsc();
}
