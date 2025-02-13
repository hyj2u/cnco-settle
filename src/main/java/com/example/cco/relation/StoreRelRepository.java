package com.example.cco.relation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRelRepository extends JpaRepository<StoreRelEntity, Long> {
  List<StoreRelEntity> findByStoreCodeAndScrapGbAndEndTimestampIsNull(String storeCode, String scrapGb);
  List<StoreRelEntity> findAllByStoreCodeAndEndTimestampIsNull(String storeCode);
  Optional<StoreRelEntity> findByScrapGbAndRelCodeAndEndTimestampIsNull(String scrapGb, String relCode);
}
