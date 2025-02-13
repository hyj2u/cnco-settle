package com.example.cco.summary;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VsettlementSummaryActiveRepository extends JpaRepository<VsettlementSummaryActiveEntity, Long> {

    List<VsettlementsSumMapping> findAllBySettlementYmdBetweenOrderBySettlementYmdDesc(Date start, Date end);

}
