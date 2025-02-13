package com.example.cco.store;

import com.example.cco.base.BaseEntity;
import com.example.cco.file.FileEntity;
import com.example.cco.security.RefreshTknEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity(name = "store")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StoreEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;

    @Column
    private String storeName;
    @Column
    private String owner;
    @Column
    private String manager;
    @Column
    private String storeCode;
    @Column
    private String activeYn;
    @Column
    private Date closingYmd;
    @Column
    private String closingDesc;
    @Column
    private Date openYmd;
    @Column
    private String phone;
    @Column
    private String storeAddress;
    @Column
    private String brandCd;
    @Column
    private String settlementCd;
    @Column
    private String contractor;
    @Column
    private Date orgContractYmd;
    @Column
    private String bank;
    @Column
    private String bankAccount;
    @Column
    private String bankAccountNumber;
    @Column
    private String email;
    @Column
    private String homeAddress;

    @Column
    private String isp;

    @Column
    private String etc;
    @Column
    private String fireInsurance;
    @Column
    private String disasterInsurance;
    @Column
    private Date writtenContractYmd;
    @Column
    private Date finalContractYmd;
    @Column
    private String storeSize;
    @Column
    private String deposit;
    @Column
    private String vat;

    @Column
    private String maint;
    @Column
    private String businessGuarantee;
    @Column
    private String charge;
    @Column
    private String openNote;
    @Column
    private Long prepaidRent;
    @Column
    private Integer prepaidMonth;
    @Column
    private Date rentFromYmd;

    @Column
    private String donationYn;






}

