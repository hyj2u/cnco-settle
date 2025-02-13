package com.example.cco.relation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VStoreRelRepository extends JpaRepository<VStoreRelEntity, String> {
    List<VStoreRelEntity> findAllByStoreCode(String storeCode);




}
