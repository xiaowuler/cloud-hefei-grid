package com.pingchuan.dto.base;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
public class WeatherElement {

    @Field("initial_time")
    private Date initialTime;

    private Coordinate coordinate;

    @Field("forecast_level")
    private Integer forecastLevel;

    private double[] loc;

    private String stationCode;

    private List<Forecast> forecasts;
}
