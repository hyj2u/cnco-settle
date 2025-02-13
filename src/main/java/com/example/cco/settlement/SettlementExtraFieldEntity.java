package com.example.cco.settlement;

import com.example.cco.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "settlement_extra_field")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SettlementExtraFieldEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;
    @Column
    private String extraGb;
    @Column
    private String fieldName;
    @Column
    private Date settlementYmd;

}

