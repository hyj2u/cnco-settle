package com.example.cco.settlement;

import java.util.Date;

public interface CustomSettlementRepository {
    void updateSettlements(String column, Integer value, Long pkey);

}
