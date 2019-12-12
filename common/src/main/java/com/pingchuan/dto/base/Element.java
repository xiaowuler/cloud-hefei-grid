package com.pingchuan.dto.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
public class Element {

    @Field("initial_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date initialTime;

    @Field("element_code")
    private String elementCode;

    @Field("mode_code")
    private String modeCode;

    @Field("org_code")
    private String orgCode;

    private Coordinate coordinate;

    @Field("forecast_interval")
    private Integer forecastInterval;

    @Field("forecast_level")
    private Integer forecastLevel;

    @Field("forecast_time")
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date forecastTime;

    @Field("time_effect")
    private Integer timeEffect;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Location> locations;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Forecast> forecasts;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double[][] values;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double[][] u_values;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double[][] v_values;
}
