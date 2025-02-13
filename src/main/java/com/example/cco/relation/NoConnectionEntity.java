package com.example.cco.relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "vno_connection")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoConnectionEntity {

    @Id
    @Column
    private String storeCode;
    @Column
    private String storeName;
    @Column
    private String gb;
}

