package com.example.cco.settlement;

import com.example.cco.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "settlement_extra_supply")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SettlementExtraSupplyEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;
    @Column
    private Date settlementYmd;
    @Column
    private String storeCode;
    @Column
    private String item;
    @Column
    private Double unitPrice;
    @Column
    private Double quantity;
    @Column
    private String note;
}

