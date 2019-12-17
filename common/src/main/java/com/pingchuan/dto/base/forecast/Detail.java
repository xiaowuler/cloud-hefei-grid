package com.pingchuan.dto.base.forecast;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Detail {

    private Integer wea;

    @Field("wind_speed")
    private double windSpeed;

    @Field("wind_direction")
    private double windDirection;

    private List<Hour> hours;

}
