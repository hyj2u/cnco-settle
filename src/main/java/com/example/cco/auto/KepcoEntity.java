package com.example.cco.auto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "vkepco")
@Data
public class KepcoEntity {

    @Id
    private String storeCode;
    @Column
    private Long elecCharge;
    @Column
    private String mstoreCode;
    @Column
    private Date settlementYmd;
}

