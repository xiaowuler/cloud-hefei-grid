package com.pingchuan.dto.base.forecast;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Day {
    private String date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double MaxTmp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double MinTmp;

    private Detail prev12Hours;

    private Detail next12Hours;
}
