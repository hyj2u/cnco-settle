package com.example.cco.relation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<RelationEntity, String> {
    Page<RelationEntity> getRelationEntitiesBySemplusCodeIsNullOrBlueorderCodeIsNullOrKepcoCodeIsNullOrGreenlogisCodeIsNullOrWaveposCodeIsNullOrMoneyonCodeIsNullOrPaycoCodeIsNullOrKakaoCodeIsNullOrderByStoreCode(Pageable pageable);



}
