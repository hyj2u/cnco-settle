package com.example.cco.relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "vstore_rel")
@Data
public class VStoreRelEntity {

    @Id
    private Long pkey;
    @Column
    private String scrapGb;
    @Column
    private String storeCode;
    @Column
    private String relCode;
   @Column
    private String relName;
}

