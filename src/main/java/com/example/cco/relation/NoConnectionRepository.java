package com.example.cco.relation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoConnectionRepository extends JpaRepository<NoConnectionEntity, String> {
  List<NoConnectionEntity> findAllByGbOrderByStoreName(String gb);
}
