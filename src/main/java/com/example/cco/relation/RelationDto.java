package com.example.cco.relation;

import lombok.Data;

import java.util.List;

@Data
public class RelationDto {

    private Long pkey;
    private String storeCode;
    private String storeName;
    private List<GreenlogisRelationDto> greenlogis;
    private List<WaveposRelationDto> wavepos;
    private List<MoneyonRelationDto> moneyon;
    private List<SemplusRelationDto> semplus;
    private List<PaycoRelationDto> payco;
    private List<BlueorderRelationDto> blueorder;
    private List<KepcoRelationDto> kepco;
    private List<KakaoRelationDto> kakao;

}

