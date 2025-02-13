package com.example.cco.summary;

import com.example.cco.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity(name = "vsettlement_summary")

@Data
@DynamicUpdate
public class VsettlementSummaryEntity  {

    @Id
    private Date settlementYmd;

    @Column
    private Long settlementAmount;
    @Column
    private Long a1cash;
    @Column
    private Long a1tax;
    @Column
    private Long a1card;
    @Column
    private Long b1rent;
    @Column
    private Long b1maint;
    @Column
    private Long b1electricity;
    @Column
    private Long b1gas;
    @Column
    private Long b1internet;
    @Column
    private Long b2charge;
    @Column
    private Long b3material;
    @Column
    private Long b3etc;
    @Column
    private Long b4initProduct;
    @Column
    private Long c1cardFee;
    @Column
    private Long c1rent;
    @Column
    private Long c1electricity;
    @Column
    private Long c1water;
    @Column
    private Long c1gas;
    @Column
    private Long c1insurance;
    @Column
    private Long c1etcExpense;
    @Column
    private Long c2interestCost;
    @Column
    private Long d1storeMaint;
    @Column
    private Long d1material;
    @Column
    private Long d1carryover;
    @Column
    private Long d2cash;
    @Column
    private Long d3rent;
    @Column
    private Long d3initProduct;
    @Column
    private Long b1etcExpense;


    @Column
    private Long a1extra1;

    @Column
    private String a1extra1name;
    @Column
    private Long a1extra2;

    @Column
    private String a1extra2name;
    @Column
    private Long a1extra3;

    @Column
    private String a1extra3name;
    @Column
    private Long a1extra4;

    @Column
    private String a1extra4name;
    @Column
    private Long a1extra5;

    @Column
    private String a1extra5name;
    @Column
    private Long b1extra1;

    @Column
    private String b1extra1name;
    @Column
    private Long b1extra2;

    @Column
    private String b1extra2name;
    @Column
    private Long b1extra3;

    @Column
    private String b1extra3name;
    @Column
    private Long b1extra4;

    @Column
    private String b1extra4name;
    @Column
    private Long b1extra5;

    @Column
    private String b1extra5name;
    @Column
    private Long b3extra1;

    @Column
    private String b3extra1name;
    @Column
    private Long b3extra2;

    @Column
    private String b3extra2name;
    @Column
    private Long b3extra3;

    @Column
    private String b3extra3name;
    @Column
    private Long b3extra4;

    @Column
    private String b3extra4name;
    @Column
    private Long b3extra5;

    @Column
    private String b3extra5name;
    @Column
    private Long b4extra1;

    @Column
    private String b4extra1name;
    @Column
    private Long b4extra2;

    @Column
    private String b4extra2name;
    @Column
    private Long b4extra3;

    @Column
    private String b4extra3name;
    @Column
    private Long b4extra4;

    @Column
    private String b4extra4name;
    @Column
    private Long b4extra5;

    @Column
    private String b4extra5name;
    @Column
    private Long c1extra1;

    @Column
    private String c1extra1name;
    @Column
    private Long c1extra2;

    @Column
    private String c1extra2name;
    @Column
    private Long c1extra3;

    @Column
    private String c1extra3name;
    @Column
    private Long c1extra4;

    @Column
    private String c1extra4name;
    @Column
    private Long c1extra5;

    @Column
    private String c1extra5name;
    @Column
    private Long c2extra1;

    @Column
    private String c2extra1name;
    @Column
    private Long c2extra2;

    @Column
    private String c2extra2name;
    @Column
    private Long c2extra3;

    @Column
    private String c2extra3name;
    @Column
    private Long c2extra4;

    @Column
    private String c2extra4name;
    @Column
    private Long c2extra5;

    @Column
    private String c2extra5name;
    @Column
    private Long d1extra1;

    @Column
    private String d1extra1name;
    @Column
    private Long d1extra2;

    @Column
    private String d1extra2name;
    @Column
    private Long d1extra3;

    @Column
    private String d1extra3name;
    @Column
    private Long d1extra4;

    @Column
    private String d1extra4name;
    @Column
    private Long d1extra5;

    @Column
    private String d1extra5name;
    @Column
    private Long d2extra1;

    @Column
    private String d2extra1name;
    @Column
    private Long d2extra2;

    @Column
    private String d2extra2name;
    @Column
    private Long d2extra3;

    @Column
    private String d2extra3name;
    @Column
    private Long d2extra4;


    @Column
    private String d2extra4name;
    @Column
    private Long d2extra5;

    @Column
    private String d2extra5name;
    @Column
    private Long d3extra1;

    @Column
    private String d3extra1name;
    @Column
    private Long d3extra2;

    @Column
    private String d3extra2name;
    @Column
    private Long d3extra3;

    @Column
    private String d3extra3name;
    @Column
    private Long d3extra4;

    @Column
    private String d3extra4name;
    @Column
    private Long d3extra5;

    @Column
    private String d3extra5name;

    @Column
    private Long storeCount;

    @Column
    private Long depositAmount;



}

