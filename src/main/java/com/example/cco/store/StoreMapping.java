package com.example.cco.store;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface StoreMapping {
    Long getPkey();

    String getStoreName();

    String getOwner();

    String getManager();

    String getStoreCode();

    String getActiveYn();

    Date getClosingYmd();

    String getClosingDesc();

    Date getOpenYmd();

    String getPhone();

    String getStoreAddress();

    String getBrandCd();
    Long getPrepaidRent();
    Integer getPrepaidMonth();

}