package com.example.cco.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface GreenlogisRepository extends JpaRepository<GreenlogisEntity, String> {
    Optional<GreenlogisEntity> findByMstoreCodeAndAndSettlementYmd(String mstoreCode, Date settlementYmd);

}
