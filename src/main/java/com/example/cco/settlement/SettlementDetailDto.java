package com.example.cco.settlement;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettlementDetailDto {
    private VsettlementTotalEntity settlementTotal;
    private List<SettlementExtraSupplyEntity> extraSupplies;

}
