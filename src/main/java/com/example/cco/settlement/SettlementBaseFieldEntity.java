package com.example.cco.settlement;

import com.example.cco.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "settlement_base_field")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SettlementBaseFieldEntity extends BaseEntity {
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;
    @Column
    private Integer order;
    @Column
    private String groupCode;
    @Column
    private String groupName;
    @Column
    private String code;
    @Column
    private String name;
    @Column
    private String defaultYn;
}

