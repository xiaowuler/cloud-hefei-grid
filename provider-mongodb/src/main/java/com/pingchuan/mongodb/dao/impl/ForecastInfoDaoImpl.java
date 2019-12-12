package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.mongodb.dao.ForecastInfoDao;
import com.pingchuan.mongodb.field.ElementField;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ForecastInfoDaoImpl implements ForecastInfoDao {

    @Override
    public List<AggregationOperation> findByForecastTime(Date forecastTime) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("forecast_infos", "element_info_id", "element_info_id", "forecast_info"));
        aggregationOperations.add(Aggregation.unwind("forecast_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("forecast_info.forecast_time").is(forecastTime).and("forecast_info.is_finished").is(true)));
        aggregationOperations.add(Aggregation.project(ElementField.forecastInfoFields).and("forecast_info._id").concat("$grid_code").as("element_value_id"));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByForecastTimeRange(Date startForecastDate, Date endForecastDate) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("forecast_infos", "element_info_id", "element_info_id", "forecast_info"));
        aggregationOperations.add(Aggregation.unwind("forecast_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("forecast_info.forecast_time").gte(startForecastDate).lte(endForecastDate).and("forecast_info.is_finished").is(true)));
        aggregationOperations.add(Aggregation.project(ElementField.forecastInfoFields).and("forecast_info._id").concat("$grid_code").as("element_value_id"));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findAll() {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("forecast_infos", "element_info_id", "element_info_id", "forecast_info"));
        aggregationOperations.add(Aggregation.unwind("forecast_info"));
        aggregationOperations.add(Aggregation.project(ElementField.forecastInfoFields).and("forecast_info._id").concat("$grid_code").as("element_value_id"));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findAllByNewest() {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("forecast_infos", "element_info_id", "element_info_id", "forecast_infos"));
        aggregationOperations.add(Aggregation.unwind("forecast_infos"));
        aggregationOperations.add(Aggregation.match(Criteria.where("forecast_infos.is_finished").is(true)));
        aggregationOperations.add(Aggregation.project("start_time", "update_time", "element_code", "forecast_model", "forecast_infos.forecast_time", "forecast_infos.time_effect").andExclude("_id"));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findAllByForecastTime(Date forecastDate) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("forecast_infos", "element_info_id", "element_info_id", "forecast_infos"));
        aggregationOperations.add(Aggregation.unwind("forecast_infos"));
        aggregationOperations.add(Aggregation.match(Criteria.where("forecast_infos.is_finished").is(true).and("forecast_infos.forecast_time").is(forecastDate)));
        aggregationOperations.add(Aggregation.project("start_time", "update_time", "element_code", "forecast_model", "forecast_infos.forecast_time", "forecast_infos.time_effect").andExclude("_id"));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByTimeEffect(String collecName, Integer timeEffect) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup(collecName, "element_info_id", "element_info_id", "forecast_info"));
        aggregationOperations.add(Aggregation.unwind("forecast_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("forecast_info.is_finished").is(true).and("forecast_info.time_effect").is(timeEffect)));
        aggregationOperations.add(Aggregation.project(ElementField.forecastInfoFields).and("forecast_info._id").concat("$grid_code").as("element_value_id").andExclude("_id"));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByTimeRange(String collecName, Date startForecastDate, Date endForecastDate) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup(collecName, "element_info_id", "element_info_id", "forecast_info"));
        aggregationOperations.add(Aggregation.unwind("forecast_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("forecast_info.is_finished").is(true).and("forecast_info.forecast_time").gte(startForecastDate).lte(endForecastDate)));
        aggregationOperations.add(Aggregation.project(ElementField.forecastInfoFields).and("forecast_info._id").concat("$grid_code").as("element_value_id").andExclude("_id"));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findOne(String collecName, Integer timeEffect) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup(collecName, "element_info_id", "element_info_id", "forecast_info"));
        aggregationOperations.add(Aggregation.unwind("forecast_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("forecast_info.is_finished").is(true).and("forecast_info.time_effect").is(timeEffect)));
        aggregationOperations.add(Aggregation.project("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level", "forecast_info.forecast_time", "forecast_info.time_effect", "forecast_info.values", "forecast_info.u_values", "forecast_info.v_values").andExclude("_id"));
        return aggregationOperations;
    }
}
