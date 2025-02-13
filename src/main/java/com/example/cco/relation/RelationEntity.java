package com.example.cco.relation;

import com.example.cco.base.BaseEntity;
import com.example.cco.security.RefreshTknEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "vstore_total_rel")
@Data
public class RelationEntity  {

    @Id
    private String storeCode;
    @Column
    private String storeName;
    @Column
    private String greenlogisCode;
    @Column
    private String greenlogisName;
    @Column
    private String waveposCode;

    @Column
    private String waveposName;
    @Column
    private String moneyonCode;
    @Column
    private String moneyonName;
    @Column
    private String semplusCode;
    @Column
    private String semplusName;
    @Column
    private String paycoCode;
    @Column
    private String paycoName;
    @Column
    private String blueorderCode;
    @Column
    private String blueorderName;
    @Column
    private String kepcoCode;
    @Column
    private String kepcoName;
    @Column
    private String kakaoCode;
    @Column
    private String kakaoName;



}

