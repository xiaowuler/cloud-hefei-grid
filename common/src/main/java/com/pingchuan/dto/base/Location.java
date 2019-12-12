package com.pingchuan.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Location {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double[] loc;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double value;

    @Field("u_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double uValue;

    @Field("v_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double vValue;
}
