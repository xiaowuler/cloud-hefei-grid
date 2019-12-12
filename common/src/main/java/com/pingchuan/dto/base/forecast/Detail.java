package com.pingchuan.dto.base.forecast;

import lombok.Data;

import java.util.List;

@Data
public class Detail {

    private Integer wea;

    private double windSpeed;

    private double windDirection;

    private List<Hour> hours;

}
