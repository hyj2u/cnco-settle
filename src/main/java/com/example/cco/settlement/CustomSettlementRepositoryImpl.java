package com.example.cco.settlement;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository

@Slf4j
public class CustomSettlementRepositoryImpl implements CustomSettlementRepository{
    private EntityManager entityManager;

    @Autowired
    public CustomSettlementRepositoryImpl(JpaContext context) {

        this.entityManager = context.getEntityManagerByManagedType(SettlementEntity.class);
    }

    @Override
    public void updateSettlements(String column, Integer value, Long pkey) {
        entityManager.createQuery("update settlement set "+ column +"="+value+" where pkey=" +pkey).executeUpdate();
    }


}
