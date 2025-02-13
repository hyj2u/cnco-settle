package com.example.cco.settlement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SettlementBaseFieldRepository extends JpaRepository<SettlementBaseFieldEntity, Long> {

    List<SettlementBaseFieldEntity> findAllByOrderByOrder();
    List<SettlementBaseFieldEntity> findAllByDefaultYnOrderByOrder(String defaultYn);

}
