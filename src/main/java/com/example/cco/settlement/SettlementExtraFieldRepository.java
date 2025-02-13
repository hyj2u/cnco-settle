package com.example.cco.settlement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SettlementExtraFieldRepository extends JpaRepository<SettlementExtraFieldEntity, Long> {
    Optional<SettlementExtraFieldEntity> findByExtraGbAndSettlementYmd(String extraGb, Date settlementYmd);
    void deleteAllBySettlementYmd(Date settlementYmd);

}
