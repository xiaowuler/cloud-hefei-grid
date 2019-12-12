package com.pingchuan.dto.base.forecast;

import lombok.Data;

@Data
public class Hour {
    private String day;
    private double tmp;
    private double windSpeed;
    private double windDirection;
}
