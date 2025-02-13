package com.example.cco.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {

    private String userId;
    private String userPw;
    private String userName;
    private String email;
    private String phone;
    private String activeYn;
    private Long pkey;
    private String auth;


}
