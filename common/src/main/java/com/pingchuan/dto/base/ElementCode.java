package com.pingchuan.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ElementCode {

    @Field("element_code")
    private String elementCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double value;

    @Field("u_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double uValue;

    @Field("v_value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double vValue;
}
