package com.pingchuan.mongodb.dao;

import com.pingchuan.model.Trapezoid;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;

import java.util.Date;
import java.util.List;

public interface TrapezoidDao {

    List<AggregationOperation> findByAreaCode(String areaCode);

    List<AggregationOperation> findByLocation(List<double[]> locations);

    List<Trapezoid> findAllTrapezoid(String areaCode);

    List<AggregationOperation> findRealByArea(String areaCode);

    List<AggregationOperation> findRealByLocation(List<double[]> locations);

    List<AggregationOperation> findByNonAreaCode();

    List<AggregationOperation> findLocationIdByElementInfo(List<double[]> locations, String elementCode, Date initialDate, String modeCode, String orgCode, Integer forecastInterval, Integer forecastLevel);

    List<AggregationOperation> findByLocation(List<double[]> locations, String trapezoidInfoId);

    List<ObjectId> findAllTrapezoidIdByLocation(List<double[]> locations);
}
