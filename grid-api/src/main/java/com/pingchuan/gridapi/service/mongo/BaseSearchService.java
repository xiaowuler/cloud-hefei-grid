package com.pingchuan.gridapi.service.mongo;

import com.pingchuan.dto.base.Element;
import com.pingchuan.dto.base.forecast.ForecastElement;
import com.pingchuan.parameter.base.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="provider-mongodb")
public interface BaseSearchService {

    @RequestMapping(value = "/baseSearch/findPointValue", method = RequestMethod.POST)
    List<Element> findPointValue(TimeEffectParameter timeEffectParameter);

    @RequestMapping(value = "/baseSearch/findLineValues", method = RequestMethod.POST)
    List<Element> findLineValues(TimeRangeParameter timeRangeParameter);

    @RequestMapping(value = "/baseSearch/findRegionValues", method = RequestMethod.POST)
    List<Element> findRegionValues(TimeEffectParameter timeEffectParameter);

    @RequestMapping(value = "/baseSearch/findWeatherForecast", method = RequestMethod.POST)
    List<ForecastElement> findWeatherForecast(ForecastParameter forecastParameter);

    @RequestMapping(value = "/baseSearch/findWeatherForecastByNewest", method = RequestMethod.POST)
    List<ForecastElement> findWeatherForecastByNewest(ForecastParameter forecastParameter);
}
