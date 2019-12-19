package com.pingchuan.dto.base.forecast;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ForecastElement {
    private double[] loc;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date initialTime;

    private String stationCode;

    private List<Day> days;
}
