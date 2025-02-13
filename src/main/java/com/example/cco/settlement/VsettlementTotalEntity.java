package com.example.cco.settlement;

import com.example.cco.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity(name = "vsettlement_total")

@Data
@DynamicUpdate
public class VsettlementTotalEntity extends BaseEntity {

    @Id
    private Long pkey;
    @Column
    private String storeCode;
    @Column
    private String storeName;
    @Column
    private String closedYn;
    @Column
    private String brandCd;
    @Column
    private String settlementCd;
    @Column
    private String brandName;
    @Column
    private String activeYn;
    @Column
    private String phone;
    @Column
    private Date settlementYmd;
    @Column
    private String owner;
    @Column
    private Integer settlementAmount;
    @Column
    private Integer a1cash;
    @Column
    private Integer a1tax;
    @Column
    private Integer a1card;
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
    @Column
    private Integer b4initProduct;
    @Column
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
    @Column
    private Integer c1etcExpense;
    @Column
    private Integer c2interestCost;
    @Column
    private Integer d1storeMaint;
    @Column
    private Integer d1material;
    @Column
    private Integer d1carryover;
    @Column
    private Integer d2cash;
    @Column
    private Integer d3rent;
    @Column
    private Integer d3initProduct;
    @Column
    private Integer b1etcExpense;
    @Column
    private String a1cashNote;
    @Column
    private String a1taxNote;
    @Column
    private String a1cardNote;
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
    @Column
    private Integer a1extra1;
    @Column
    private String a1extra1note;
    @Column
    private String a1extra1name;
    @Column
    private Integer a1extra2;
    @Column
    private String a1extra2note;
    @Column
    private String a1extra2name;
    @Column
    private Integer a1extra3;
    @Column
    private String a1extra3note;
    @Column
    private String a1extra3name;
    @Column
    private Integer a1extra4;
    @Column
    private String a1extra4note;
    @Column
    private String a1extra4name;
    @Column
    private Integer a1extra5;
    @Column
    private String a1extra5note;
    @Column
    private String a1extra5name;
    @Column
    private Integer b1extra1;
    @Column
    private String b1extra1note;
    @Column
    private String b1extra1name;
    @Column
    private Integer b1extra2;
    @Column
    private String b1extra2note;
    @Column
    private String b1extra2name;
    @Column
    private Integer b1extra3;
    @Column
    private String b1extra3note;
    @Column
    private String b1extra3name;
    @Column
    private Integer b1extra4;
    @Column
    private String b1extra4note;
    @Column
    private String b1extra4name;
    @Column
    private Integer b1extra5;
    @Column
    private String b1extra5note;
    @Column
    private String b1extra5name;
    @Column
    private Integer b3extra1;
    @Column
    private String b3extra1note;
    @Column
    private String b3extra1name;
    @Column
    private Integer b3extra2;
    @Column
    private String b3extra2note;
    @Column
    private String b3extra2name;
    @Column
    private Integer b3extra3;
    @Column
    private String b3extra3note;
    @Column
    private String b3extra3name;
    @Column
    private Integer b3extra4;
    @Column
    private String b3extra4note;
    @Column
    private String b3extra4name;
    @Column
    private Integer b3extra5;
    @Column
    private String b3extra5note;
    @Column
    private String b3extra5name;
    @Column
    private Integer b4extra1;
    @Column
    private String b4extra1note;
    @Column
    private String b4extra1name;
    @Column
    private Integer b4extra2;
    @Column
    private String b4extra2note;
    @Column
    private String b4extra2name;
    @Column
    private Integer b4extra3;
    @Column
    private String b4extra3note;
    @Column
    private String b4extra3name;
    @Column
    private Integer b4extra4;
    @Column
    private String b4extra4note;
    @Column
    private String b4extra4name;
    @Column
    private Integer b4extra5;
    @Column
    private String b4extra5note;
    @Column
    private String b4extra5name;
    @Column
    private Integer c1extra1;
    @Column
    private String c1extra1note;
    @Column
    private String c1extra1name;
    @Column
    private Integer c1extra2;
    @Column
    private String c1extra2note;
    @Column
    private String c1extra2name;
    @Column
    private Integer c1extra3;
    @Column
    private String c1extra3note;
    @Column
    private String c1extra3name;
    @Column
    private Integer c1extra4;
    @Column
    private String c1extra4note;
    @Column
    private String c1extra4name;
    @Column
    private Integer c1extra5;
    @Column
    private String c1extra5note;
    @Column
    private String c1extra5name;
    @Column
    private Integer c2extra1;
    @Column
    private String c2extra1note;
    @Column
    private String c2extra1name;
    @Column
    private Integer c2extra2;
    @Column
    private String c2extra2note;
    @Column
    private String c2extra2name;
    @Column
    private Integer c2extra3;
    @Column
    private String c2extra3note;
    @Column
    private String c2extra3name;
    @Column
    private Integer c2extra4;
    @Column
    private String c2extra4note;
    @Column
    private String c2extra4name;
    @Column
    private Integer c2extra5;
    @Column
    private String c2extra5note;
    @Column
    private String c2extra5name;
    @Column
    private Integer d1extra1;
    @Column
    private String d1extra1note;
    @Column
    private String d1extra1name;
    @Column
    private Integer d1extra2;
    @Column
    private String d1extra2note;
    @Column
    private String d1extra2name;
    @Column
    private Integer d1extra3;
    @Column
    private String d1extra3note;
    @Column
    private String d1extra3name;
    @Column
    private Integer d1extra4;
    @Column
    private String d1extra4note;
    @Column
    private String d1extra4name;
    @Column
    private Integer d1extra5;
    @Column
    private String d1extra5note;
    @Column
    private String d1extra5name;
    @Column
    private Integer d2extra1;
    @Column
    private String d2extra1note;
    @Column
    private String d2extra1name;
    @Column
    private Integer d2extra2;
    @Column
    private String d2extra2note;
    @Column
    private String d2extra2name;
    @Column
    private Integer d2extra3;
    @Column
    private String d2extra3note;
    @Column
    private String d2extra3name;
    @Column
    private Integer d2extra4;
    @Column
    private String d2extra4note;

    @Column
    private String d2extra4name;
    @Column
    private Integer d2extra5;
    @Column
    private String d2extra5note;
    @Column
    private String d2extra5name;
    @Column
    private Integer d3extra1;
    @Column
    private String d3extra1note;
    @Column
    private String d3extra1name;
    @Column
    private Integer d3extra2;
    @Column
    private String d3extra2note;
    @Column
    private String d3extra2name;
    @Column
    private Integer d3extra3;
    @Column
    private String d3extra3note;
    @Column
    private String d3extra3name;
    @Column
    private Integer d3extra4;
    @Column
    private String d3extra4note;
    @Column
    private String d3extra4name;
    @Column
    private Integer d3extra5;
    @Column
    private String d3extra5note;
    @Column
    private String d3extra5name;
    @Column
    private String settlementTitle;
    @Column
    private Long depositAmount;
    @Column
    private String bank;
    @Column
    private String bankAccountNumber;

    @Column
    private String bankAccount;
    @Column(name = "a_total")
    private Long atotal;
    @Column(name = "b_total")
    private Long btotal;
    @Column
    private Long salesProfit;



}

