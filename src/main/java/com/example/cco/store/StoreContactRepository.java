package com.example.cco.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreContactRepository extends JpaRepository<StoreContractEntity, Long> {
  List<StoreContractEntity> findAllByStoreCodeOrderByContractStartYmdDesc(String storeCode);

  void deleteAllByStoreCode(String storeCode);

  Optional<StoreContractEntity>
  findTopByStoreCodeAndContractStartYmdLessThanEqualAndContractEndYmdGreaterThanEqualOrderByPkey(String storeCode, Date contractStartYmd, Date contractEndYmd);

}
