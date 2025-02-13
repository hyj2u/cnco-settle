package com.example.cco.code;

import com.example.cco.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "code")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CodeEntity  {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;

    @Column
    private String code;
    @Column
    private String value;
    @Column
    private String useYn;
    @Column
    private Integer order;
    @Column
    private String gb;




}

