package com.example.cco.settlement;

import com.example.cco.store.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SettlementRepository extends JpaRepository<SettlementEntity, Long>, CustomSettlementRepository {
    Optional<SettlementEntity> findByStoreCodeAndSettlementYmd(String storeCode, Date settlementYmd);

    List<SettlementEntity> findAllBySettlementYmd(Date settlementYmd);
}
