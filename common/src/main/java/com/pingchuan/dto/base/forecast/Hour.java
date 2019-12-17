package com.pingchuan.dto.base.forecast;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Hour {
    private String day;
    private double tmp;

    @Field("wind_speed")
    private double windSpeed;
    @Field("wind_direction")
    private double windDirection;
}
