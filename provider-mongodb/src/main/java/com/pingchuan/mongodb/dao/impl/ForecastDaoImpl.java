package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.dto.base.forecast.Day;
import com.pingchuan.dto.base.forecast.ForecastElement;
import com.pingchuan.model.Trapezoid;
import com.pingchuan.mongodb.dao.ForecastDao;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ForecastDaoImpl implements ForecastDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ForecastElement> findOneWeekByLocation(List<double[]> locations) {
        List<ForecastElement> forecastElements = new ArrayList<>();
        for(double[] loc : locations){
            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            aggregationOperations.add(Aggregation.geoNear(NearQuery.near(loc[0], loc[1]).spherical(true), "distance"));
            aggregationOperations.add(Aggregation.sort(Sort.Direction.ASC, "loc"));
            aggregationOperations.add(Aggregation.limit(10));
            aggregationOperations.add(Aggregation.sort(Sort.Direction.ASC, "date"));

            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            List<Day> days = mongoTemplate.aggregate(aggregation, "forecasts", Day.class).getMappedResults();
            ForecastElement forecastElement = createForecast(days);
            if (!StringUtils.isEmpty(forecastElement)){
                forecastElements.add(forecastElement);
            }
        }
        return forecastElements;
    }

    private ForecastElement createForecast(List<Day> days){
        if(days.size() > 0){
            ForecastElement forecastElement = new ForecastElement();
            forecastElement.setLoc(days.get(0).getLoc());
            forecastElement.setDays(days.subList(0, 7));
            return forecastElement;
        }

        return null;
    }
}
