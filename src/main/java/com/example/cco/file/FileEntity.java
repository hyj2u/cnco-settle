package com.example.cco.file;

import com.example.cco.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "files")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class FileEntity extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long pkey;

    @Column
    private String fileType;
    @Column
    private String fileName;
    @Column
    private String filePath;
    @Column
    private String storeCode;




}

