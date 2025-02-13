package com.example.cco.store;

import com.example.cco.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "store_contract")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StoreContractEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;

    @Column
    private String storeCode;
    @Column
    private Date contractStartYmd;
    @Column
    private Date contractEndYmd;
    @Column
    private Integer rent;








}

