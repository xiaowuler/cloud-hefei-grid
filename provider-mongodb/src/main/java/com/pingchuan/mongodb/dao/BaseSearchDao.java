package com.pingchuan.mongodb.dao;

import com.pingchuan.dto.base.Element;
import com.pingchuan.dto.base.WeatherElement;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.List;

public interface BaseSearchDao {

    List<Element> findPointValue(List<AggregationOperation> aggregationOperations);

    List<Element> findLineValues(List<AggregationOperation> aggregationOperations);

    List<Element> findRegionValues(List<AggregationOperation> aggregationOperations);

    List<WeatherElement> findWeatherForecast(List<AggregationOperation> aggregationOperations);
}
