package com.example.cco.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface KepcoRepository extends JpaRepository<KepcoEntity, String> {
    Optional<KepcoEntity> findByMstoreCodeAndSettlementYmd(String mstoreCode, Date settlementYmd);

}
