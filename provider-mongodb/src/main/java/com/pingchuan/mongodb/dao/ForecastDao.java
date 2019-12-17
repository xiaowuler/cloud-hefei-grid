package com.pingchuan.mongodb.dao;

import com.pingchuan.dto.base.forecast.ForecastElement;

import java.util.Date;
import java.util.List;

public interface ForecastDao {
    List<ForecastElement> findOneWeekByLocation(List<double[]> locations);
}
