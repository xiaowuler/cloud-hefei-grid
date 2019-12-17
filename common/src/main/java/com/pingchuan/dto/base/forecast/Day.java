package com.pingchuan.dto.base.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class Day {

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private double[] loc;

    @Field("max_tmp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double MaxTmp;

    @Field("min_tmp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double MinTmp;

    @Field("prev_12_hours")
    private Detail prev12Hours;

    @Field("next_12_hours")
    private Detail next12Hours;
}
