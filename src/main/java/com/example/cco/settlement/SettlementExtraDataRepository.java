package com.example.cco.settlement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SettlementExtraDataRepository extends JpaRepository<SettlementExtraDataEntity, Long> {
        Optional<SettlementExtraDataEntity> findBySettlementYmdAndExtraFieldAndStoreCode(Date settlementYmd,String extraField, String storeCode);

        void deleteAllBySettlementYmdAndStoreCode(Date settlementYmd, String storeCode);
        void deleteAllBySettlementYmd(Date settlementYmd);

}
