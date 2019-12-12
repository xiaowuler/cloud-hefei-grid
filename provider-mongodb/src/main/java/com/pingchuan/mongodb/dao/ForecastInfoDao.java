package com.pingchuan.mongodb.dao;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.Date;
import java.util.List;

public interface ForecastInfoDao {
    List<AggregationOperation> findByForecastTime(Date forecastTime);

    List<AggregationOperation> findByForecastTimeRange(Date startForecastDate, Date endForecastDate);

    List<AggregationOperation> findAll();

    List<AggregationOperation> findAllByNewest();

    List<AggregationOperation> findAllByForecastTime(Date forecastDate);

    List<AggregationOperation> findByTimeEffect(String collecName, Integer timeEffect);

    List<AggregationOperation> findByTimeRange(String covertDateToString, Date startForecastDate, Date endForecastDate);

    List<AggregationOperation> findOne(String covertDateToString, Integer timeEffect);
}
