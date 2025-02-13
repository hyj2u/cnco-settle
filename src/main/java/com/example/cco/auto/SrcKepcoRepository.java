package com.example.cco.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SrcKepcoRepository extends JpaRepository<SrcKepcoEntity, Long> {

    Optional<SrcKepcoEntity> findByStoreCodeAndPaymentYmdAndEndTimestampIsNull(String storeCode, Date paymentYmd);

  
}
