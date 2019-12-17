package com.pingchuan.mongodb.controller;

import com.pingchuan.dto.base.Element;
import com.pingchuan.dto.base.forecast.ForecastElement;
import com.pingchuan.mongodb.service.BaseSearchService;
import com.pingchuan.parameter.base.ForecastParameter;
import com.pingchuan.parameter.base.TimeEffectParameter;
import com.pingchuan.parameter.base.TimeRangeParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("baseSearch")
public class BaseSearchController {

    @Autowired
    private BaseSearchService baseSearchService;

    @PostMapping("/findPointValue")
    public List<Element> findPointValue(@RequestBody TimeEffectParameter timeEffectParameter){
        return baseSearchService.findPointValue(timeEffectParameter);
    }

    @PostMapping("/findLineValues")
    public List<Element> findLineValues(@RequestBody TimeRangeParameter timeRangeParameter){
        return baseSearchService.findLineValues(timeRangeParameter);
    }

    @PostMapping(value = "/findRegionValues")
    public List<Element> findRegionValues(@RequestBody TimeEffectParameter timeEffectParameter){
        return baseSearchService.findRegionValues(timeEffectParameter);
    }

    @PostMapping(value = "/findWeatherForecast")
    public List<ForecastElement> findWeatherForecast(@RequestBody ForecastParameter forecastParameter){
        return baseSearchService.findWeatherForecast(forecastParameter);
    }

    @PostMapping(value = "/findWeatherForecastByNewest")
    public List<ForecastElement> findWeatherForecastByNewest(@RequestBody ForecastParameter forecastParameter){
        return baseSearchService.findWeatherForecastByNewest(forecastParameter);
    }

}
