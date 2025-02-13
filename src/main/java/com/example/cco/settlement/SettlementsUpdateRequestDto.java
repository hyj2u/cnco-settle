package com.example.cco.settlement;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettlementsUpdateRequestDto {
    private Long pkey;
    private String code;
    private Integer value;

}
