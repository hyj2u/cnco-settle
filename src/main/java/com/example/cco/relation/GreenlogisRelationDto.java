package com.example.cco.relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GreenlogisRelationDto {

    private String greenlogisCode;
    private String greenlogisName;

}

