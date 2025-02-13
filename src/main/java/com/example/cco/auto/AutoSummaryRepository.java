package com.example.cco.auto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoSummaryRepository extends JpaRepository<AutoSummaryEntity, String> {


}
