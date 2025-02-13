package com.example.cco.settlement;

import com.example.cco.base.BaseEntity;
import com.example.cco.store.StoreEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "settlement")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SettlementEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;
    @Column
    private String storeCode;
    @Column
    private Date settlementYmd;
    @Column
    private String closedYn;
    @Column
    private Integer a1cash;
    @Column
    private Integer a1card;
    @Column
    private Integer a1tax;
    @Column
    private Integer b1rent;
    @Column
    private Integer b1maint;
    @Column
    private Integer b1electricity;
    @Column
    private Integer b1gas;
    @Column
    private Integer b1internet;
    @Column
    private Integer b2charge;
    @Column
    private Integer b3material;
    @Column
    private Integer b3etc;
    @Column(name = "b4init_product")
    private Integer b4initProduct;
    @Column(name = "c1card_fee")
    private Integer c1cardFee;
    @Column
    private Integer c1rent;
    @Column
    private Integer c1electricity;
    @Column
    private Integer c1water;
    @Column
    private Integer c1gas;
    @Column
    private Integer c1insurance;
    @Column(name = "c1etc_expense")
    private Integer c1etcExpense;
    @Column(name="c2interest_cost")
    private Integer c2interestCost;
    @Column(name = "d1store_maint")
    private Integer d1storeMaint;
    @Column
    private Integer d1material;
    @Column
    private Integer d1carryover;
    @Column
    private Integer d2cash;
    @Column
    private Integer d3rent;
    @Column(name = "d3init_product")
    private Integer d3initProduct;
    @Column(name = "b1etc_expense")
    private Integer b1etcExpense;

    @Column
    private String a1cashNote;
    @Column
    private String a1cardNote;
    @Column
    private String a1taxNote;
    @Column
    private String b1rentNote;
    @Column
    private String b1maintNote;
    @Column
    private String b1electricityNote;
    @Column
    private String b1gasNote;
    @Column
    private String b1internetNote;
    @Column
    private String b2chargeNote;
    @Column
    private String b3materialNote;
    @Column
    private String b3etcNote;
    @Column
    private String b1etcExpenseNote;
    @Column
    private String b4initProductNote;
    @Column
    private String c1cardFeeNote;
    @Column
    private String c1rentNote;
    @Column
    private String c1electricityNote;
    @Column
    private String c1waterNote;
    @Column
    private String c1gasNote;
    @Column
    private String c1insuranceNote;
    @Column
    private String c1etcExpenseNote;
    @Column
    private String c2interestCostNote;
    @Column
    private String d1storeMaintNote;
    @Column
    private String d1materialNote;
    @Column
    private String d1carryoverNote;
    @Column
    private String d2cashNote;
    @Column
    private String d3rentNote;
    @Column
    private String d3initProductNote;
    @Column(name = "src_a1cash")
    private Integer srcA1cash;
    @Column(name = "src_a1card")
    private Integer srcA1card;
    @Column(name = "src_b1electricity")
    private Integer srcB1electricity;
    @Column(name = "src_b3material")
    private Integer srcB3material;
    @Column(name = "src_c1card_fee")
    private Integer srcC1cardFee;
    @Column(name = "src_D1material")
    private Integer srcD1material;



}

