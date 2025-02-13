package com.example.cco.settlement;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public interface VsettlementTotalStoreMapping {
    public Date getSettlementYmd();

    public Long getAtotal();

    public Long getBtotal();

    public Long getSalesProfit();

    public Long getDepositAmount();

    public Long getSettlementAmount();

    public Long getPkey();

}
