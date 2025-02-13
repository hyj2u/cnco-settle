package com.example.cco.auto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "vauto_total")
@Data
public class AutoTotalEntity {
    @Column
    private String brandName;
    @Column
    private String brandCd;
    @Id
    private String storeCode;
    @Column
    private String storeName;
    @Column
    private Integer glTaxation;
    @Column
    private Integer glTaxExemption;
    @Column
    private Integer kcElecCharge;
    @Column
    private Integer pcTotalAmount;
    @Column
    private Integer pcPoint;
    @Column
    private Integer pcCoupon;
    @Column
    private Integer moCard;
    @Column
    private Integer moCardBackup;
    @Column
    private Integer moCashReceipt;
    @Column
    private Integer moCashReceiptBackup;
    @Column
    private Integer spCard;
    @Column
    private Integer spCashReceipt;
    @Column
    private Integer wpCash;
    @Column
    private Integer boTotalSales;
    @Column
    private Integer kaKakaoCard;
    @Column
    private String settlementCd;

}

