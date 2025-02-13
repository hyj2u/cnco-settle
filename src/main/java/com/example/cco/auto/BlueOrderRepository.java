package com.example.cco.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BlueOrderRepository extends JpaRepository<BlueorderEntity, String> {

    Optional<BlueorderEntity> findByMstoreCodeAndSettlementYmd(String mstoreCode, Date settlementYmd);
}
