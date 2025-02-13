package com.example.cco.relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StoreTerminalDto {
    private String storeCode;
    private List<String> relCode;
}

