package com.pingchuan.dto.base.forecast;

import lombok.Data;

import java.util.List;

@Data
public class ForecastElement {
    private double[] loc;
    private List<Day> days;
}
