package com.example.cco.auto;

import com.example.cco.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "src_kepco")
@Data
public class SrcKepcoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkey;
    @Column
    private String storeCode;
    @Column
    private String address;
    @Column
    private Integer elecUsage;
    @Column
    private Integer elecCharge;
    @Column
    private Integer tvCharge;
    @Column
    private Integer totalCharge;
    @Column
    private Date paymentYmd;
    @Column
    private LocalDateTime endTimestamp;
    @Column
    private LocalDateTime insertTimestamp;



}

