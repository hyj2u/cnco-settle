package com.example.cco.auto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "vauto_summary")
@Data
public class AutoSummaryEntity {

    @Id
    private String storeCode;
    @Column
    private String storeName;
    @Column
    private Integer cash;
    @Column
    private Integer card;
    @Column
    private Integer taxation;
    @Column
    private Integer taxExemption;
    @Column
    private Integer elecCharge;

}

