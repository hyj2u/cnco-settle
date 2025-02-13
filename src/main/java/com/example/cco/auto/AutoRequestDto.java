package com.example.cco.auto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AutoRequestDto {
  private String settlementYmd;
  private List<AutoResponsetDto> autos;

}
