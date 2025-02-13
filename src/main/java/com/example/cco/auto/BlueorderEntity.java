package com.example.cco.auto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "vblueorder")
@Data
public class BlueorderEntity {

    @Id
    private String storeCode;
    @Column
    private String storeName;
    @Column
    private Date settlementYmd;
    @Column
    private Long totalSales;
    @Column
    private String mstoreCode;

}

