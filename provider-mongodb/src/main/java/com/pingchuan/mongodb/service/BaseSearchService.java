package com.pingchuan.mongodb.service;

import com.pingchuan.dto.base.Element;
import com.pingchuan.dto.base.forecast.ForecastElement;
import com.pingchuan.parameter.base.ForecastParameter;
import com.pingchuan.parameter.base.TimeEffectParameter;
import com.pingchuan.parameter.base.TimeRangeParameter;

import java.util.List;

public interface BaseSearchService {
    List<Element> findPointValue(TimeEffectParameter timeEffectParameter);

    List<Element> findLineValues(TimeRangeParameter timeRangeParameter);

    List<Element> findRegionValues(TimeEffectParameter timeEffectParameter);

    List<ForecastElement> findWeatherForecast(ForecastParameter forecastParameter);
}
