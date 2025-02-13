package com.example.cco.settlement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SettlementExtraSupplyRepository extends JpaRepository<SettlementExtraSupplyEntity, Long> {
        List<SettlementExtraSupplyEntity> findAllBySettlementYmdAndStoreCode(Date settlementYmd, String storeCode);
        void deleteAllBySettlementYmdAndStoreCode(Date settlementYmd, String storeCode);

}
