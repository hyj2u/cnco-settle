package com.example.cco.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface MemberMapping {
    Long getPkey();

    String getUserId();

    String getUserName();

    String getEmail();

    String getPhone();

    String getUserAuthCd();


}