package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.dto.base.Element;
import com.pingchuan.dto.base.WeatherElement;
import com.pingchuan.mongodb.dao.BaseSearchDao;
import com.pingchun.utils.TimeUtil;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.bind;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Repository
public class BaseSearchDaoImpl implements BaseSearchDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Element> findPointValue(List<AggregationOperation> aggregationOperations) {

        aggregationOperations.add(Aggregation.project("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level", "forecast_time", "time_effect").and("location").nested(bind("loc", "loc").and("value").and("u_value").and("v_value")));
        aggregationOperations.add(Aggregation.group("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level", "forecast_time", "time_effect").push("location").as("locations"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Element> elements = mongoTemplate.aggregate(aggregation, "element_infos", Element.class).getMappedResults();
        return elements;
    }

    @Override
    public List<Element> findLineValues(List<AggregationOperation> aggregationOperations) {

        aggregationOperations.add(Aggregation.project("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level", "forecast_time", "time_effect").and("location").nested(bind("loc", "loc").and("value").and("u_value").and("v_value")));
        aggregationOperations.add(Aggregation.group("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level", "forecast_time", "time_effect").push("location").as("locations"));

        aggregationOperations.add(Aggregation.project("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level").and("forecast").nested(bind("forecast_time", "forecast_time").and("time_effect").and("locations")));
        aggregationOperations.add(Aggregation.group("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level").push("forecast").as("forecasts"));


        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Element> elements = mongoTemplate.aggregate(aggregation, "element_infos", Element.class).getMappedResults();
        return elements;
    }

    @Override
    public List<Element> findRegionValues(List<AggregationOperation> aggregationOperations) {
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Element> elements = mongoTemplate.aggregate(aggregation, "element_infos", Element.class).getMappedResults();
        return elements;
    }

    @Override
    public List<WeatherElement> findWeatherForecast(List<AggregationOperation> aggregationOperations) {

        //aggregationOperations.add(project("initial_time", "element_code", "coordinate", "forecast_level", "loc", "forecast_time", "value", "u_value", "v_value").and(DateOperators.dateOf("forecast_time").withTimezone(DateOperators.Timezone.valueOf("+08:00")).hour()).as("hour").and(DateOperators.dateOf("forecast_time").withTimezone(DateOperators.Timezone.valueOf("+08:00")).toString("%Y-%m-%d")).as("day"));
        //aggregationOperations.add(Aggregation.project("initial_time", "element_code", "coordinate", "forecast_level", "loc", "forecast_time", "value", "u_value", "v_value", "day", "hour").and("$hour").gte(8).lt(20).as("is_midnight"));
        Fields elementCode = Fields.fields("initial_time", "coordinate", "forecast_level", "loc", "forecast_time");
        aggregationOperations.add(Aggregation.project(elementCode).and("element_code").nested(bind("element_code", "element_code").and("value").and("u_value").and("v_value")));
        aggregationOperations.add(Aggregation.group(elementCode).push("element_code").as("element_codes"));

        Fields forecast = Fields.fields("initial_time", "coordinate", "forecast_level", "loc");
        aggregationOperations.add(Aggregation.project(forecast).and("forecast").nested(bind("forecast_time", "forecast_time").and("element_codes")));
        aggregationOperations.add(Aggregation.group(forecast).push("forecast").as("forecasts"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<WeatherElement> elements = mongoTemplate.aggregate(aggregation, "element_infos", WeatherElement.class).getMappedResults();
        return elements;
    }


}
