package com.example.cco.auto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "vgreenlogis")
@Data
public class GreenlogisEntity {

    @Id
    private String storeCode;
    @Column
    private String storeName;
    @Column
    private Date settlementYmd;
    @Column
    private Long taxation;
    @Column
    private Long taxExemption;
    @Column
    private String mstoreCode;
}

