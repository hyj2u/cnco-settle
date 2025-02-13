package com.example.cco.relation;

import com.example.cco.base.PageDto;
import lombok.Data;

import java.util.List;

@Data
public class RelationNeedDto {

    private PageDto page;
    private List<RelationDto> relations;

}

