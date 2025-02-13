package com.example.cco.relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "store_rel")
@Data
public class StoreRelEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;
    @Column
    private String scrapGb;
    @Column
    private String storeCode;
    @Column
    private String relCode;
    @Column
    private LocalDateTime startTimestamp;
    @Column
    private LocalDateTime endTimestamp;
}

