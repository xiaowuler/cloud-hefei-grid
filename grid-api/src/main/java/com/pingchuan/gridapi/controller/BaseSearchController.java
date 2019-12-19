package com.pingchuan.gridapi.controller;

import com.pingchuan.contants.ResultCode;
import com.pingchuan.dto.base.Element;
import com.pingchuan.dto.base.forecast.ForecastElement;
import com.pingchuan.parameter.base.*;
import com.pingchuan.gridapi.annotation.BaseAction;
import com.pingchuan.gridapi.domain.ApiResponse;
import com.pingchuan.gridapi.service.mongo.BaseSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("baseSearch")
@RestController
public class BaseSearchController {

    @Autowired
    private BaseSearchService baseSearchService;

    @RequestMapping("/findPointValue")
    @BaseAction(apiId = 1, isNeedLocation = true)
    public ApiResponse findPointValue(TimeEffectParameter timeEffectParameter){
        List<Element> elements = baseSearchService.findPointValue(timeEffectParameter);
        return response(elements);
    }

    @RequestMapping("/findLineValues")
    @BaseAction(apiId = 2, isNeedLocation = true)
    public ApiResponse findLineValues(TimeRangeParameter timeRangeParameter){
        List<Element> elements = baseSearchService.findLineValues(timeRangeParameter);
        return response(elements);
    }

    @RequestMapping("/findRegionValues")
    @BaseAction(apiId = 3, isNeedLocation = false)
    public ApiResponse findRegionValues(TimeEffectParameter timeEffectParameter){
        List<Element> elements = baseSearchService.findRegionValues(timeEffectParameter);
        return response(elements);
    }

    @RequestMapping("/findWeatherForecast")
    @BaseAction(apiId = 4, isNeedLocation = true)
    public ApiResponse findWeatherForecast(ForecastParameter forecastParameter){
        List<ForecastElement> forecastElements = baseSearchService.findWeatherForecast(forecastParameter);
        if (forecastElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", forecastElements);
    }

    private ApiResponse response(List<Element> elements){
        if (elements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", elements.get(0));
    }

    @RequestMapping("/findWeatherForecastByNewest")
    @BaseAction(apiId = 5, isNeedLocation = true)
    public ApiResponse findWeatherForecastByNewest(ForecastParameter forecastParameter){
        List<ForecastElement> forecastElements = baseSearchService.findWeatherForecastByNewest(forecastParameter);
        if (forecastElements.size() == 0) {
            return new ApiResponse(ResultCode.NULL_VALUE, "未查询到值", null);
        }
        return new ApiResponse(ResultCode.SUCCESS, "查询成功", forecastElements);
    }

}
