package com.pingchuan.mongodb.dao;

import com.pingchuan.model.ElementInfo;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.Date;
import java.util.List;

public interface ElementInfoDao {
    List<AggregationOperation> findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(Date updateTime, Date startTime, String elementCode, String forecastModel);

    List<AggregationOperation> findByForecastModel(String forecastModel, String elementCode);

    List<AggregationOperation> findByForecastModelAndDate(String forecastModel, Date startTime, Date endTime);

    List<AggregationOperation> findOne(String elementCode, Date initialDate, String modeCode, String orgCode, Integer forecastInterval, Integer forecastLevel);

    List<ElementInfo> findByInitialTime(Date initialTime);

    List<AggregationOperation> findOneById(List<Long> ids);

    List<ElementInfo> findSevenDayInitialTimes();
}
