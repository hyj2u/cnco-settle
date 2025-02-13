package com.example.cco.settlement;

import com.example.cco.base.PageDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SettlementListDto {
    private List<VsettlementsListMapping> settlements;
    private PageDto page;

}
