package com.example.cco.base;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorRespDto {
    private String msg;
    private String errorCode;

}
