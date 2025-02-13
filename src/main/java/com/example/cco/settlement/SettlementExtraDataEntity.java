package com.example.cco.settlement;

import com.example.cco.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "settlement_extra_data")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SettlementExtraDataEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;
    @Column
    private String storeCode;
    @Column
    private String extraField;
    @Column
    private String note;
    @Column
    private Integer amount;
    @Column
    private Date settlementYmd;

}

