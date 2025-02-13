package com.example.cco.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface MoneyonRepository extends JpaRepository<MoneyonEntity, String> {
    Optional<MoneyonEntity> findByMstoreCodeAndSettlementYmd(String mstoreCode, Date settlemetYmd);

}
