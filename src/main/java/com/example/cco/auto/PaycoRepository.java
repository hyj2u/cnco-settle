package com.example.cco.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface PaycoRepository extends JpaRepository<PaycoEntity, String> {
    Optional<PaycoEntity> findByMstoreCodeAndSettlementYmd(String mstoreCode, Date settlementYmd);

}
