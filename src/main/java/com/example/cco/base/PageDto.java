package com.example.cco.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageDto {

    private int totalPage;
    private int number;
    private Long totalElements;
}
