package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.model.Trapezoid;
import com.pingchuan.mongodb.dao.TrapezoidDao;
import com.pingchuan.mongodb.field.ElementField;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TrapezoidDaoImpl implements TrapezoidDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<AggregationOperation> findByAreaCode(String areaCode) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid.area_code").is(areaCode)));
        aggregationOperations.add(Aggregation.project(ElementField.trapezoidFields));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByLocation(List<double[]> locations) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid._id").in(findAllTrapezoidIdByLocation(locations))));
        aggregationOperations.add(Aggregation.project(ElementField.trapezoidFields));
        return aggregationOperations;
    }

    @Override
    public List<Trapezoid> findAllTrapezoid(String areaCode) {

        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        if(!StringUtils.isEmpty(areaCode)) {
            aggregationOperations.add(Aggregation.match(Criteria.where("area_code").is(areaCode)));
        };
        aggregationOperations.add(Aggregation.project("grid_code", "loc", "area_code", "area_name").andExclude("_id"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        return mongoTemplate.aggregate(aggregation, "trapezoids", Trapezoid.class).getMappedResults();
    }

    @Override
    public List<AggregationOperation> findRealByArea(String areaCode) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid.area_code").is(areaCode)));
        aggregationOperations.add(Aggregation.project("real_time", "update_time", "element_code", "trapezoid.area_code", "trapezoid.grid_code", "trapezoid.loc", "trapezoid.area_name").and("$real_info_id").concat("$trapezoid.grid_code").as("element_value_id"));

        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByNonAreaCode() {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid.area_code").ne(null)));
        aggregationOperations.add(Aggregation.project(ElementField.trapezoidFields));
        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findLocationIdByElementInfo(List<double[]> locations, String elementCode, Date initialDate, String modeCode, String orgCode, Integer forecastInterval, Integer forecastLevel) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid._id").in(findAllTrapezoidIdByLocation(locations, elementCode, initialDate, modeCode, orgCode, forecastInterval, forecastLevel))));
        aggregationOperations.add(Aggregation.project(ElementField.trapezoidFields));

        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByLocation(List<double[]> locations, String trapezoidInfoId) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid._id").in(findAllTrapezoidIdByLocation(locations, trapezoidInfoId))));
        aggregationOperations.add(Aggregation.project(ElementField.trapezoidFields));

        return aggregationOperations;
    }

    private List<ObjectId> findAllTrapezoidIdByLocation(List<double[]> locations, String trapezoidInfoId) {
        List<ObjectId> trapezoidIds = new ArrayList<>();
        for (double[] loc : locations) {
            if (StringUtils.isEmpty(loc)) {
                continue;
            }

            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            aggregationOperations.add(Aggregation.geoNear(NearQuery.near(loc[0], loc[1]).spherical(true), "distance"));
            aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid_info_id").is(trapezoidInfoId)));

            aggregationOperations.add(Aggregation.limit(1));
            aggregationOperations.add(Aggregation.project("_id"));

            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            List<Trapezoid> trapezoids = mongoTemplate.aggregate(aggregation, "trapezoids", Trapezoid.class).getMappedResults();
            trapezoids.forEach(t -> trapezoidIds.add(t.getId()));
        }

        return trapezoidIds;
    }

    private List<ObjectId> findAllTrapezoidIdByLocation(List<double[]> locations, String elementCode, Date initialDate, String modeCode, String orgCode, Integer forecastInterval, Integer forecastLevel) {
        List<ObjectId> trapezoidIds = new ArrayList<>();
        for (double[] loc : locations) {
            if (StringUtils.isEmpty(loc)) {
                continue;
            }

            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            aggregationOperations.add(Aggregation.geoNear(NearQuery.near(loc[0], loc[1]).spherical(true), "distance"));
            aggregationOperations.add(Aggregation.lookup("element_infos", "trapezoid_info_id", "trapezoid_info_id", "element_infos"));
            aggregationOperations.add(Aggregation.unwind("element_infos"));
            aggregationOperations.add(Aggregation.match(Criteria.where("element_infos.element_code").is(elementCode).and("element_infos.initial_time").is(initialDate).and("element_infos.mode_code").is(modeCode).and("element_infos.org_code").is(orgCode).and("element_infos.forecast_interval").is(forecastInterval).and("element_infos.forecast_level").is(forecastLevel)));

            aggregationOperations.add(Aggregation.limit(1));
            aggregationOperations.add(Aggregation.project("_id"));

            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            List<Trapezoid> trapezoids = mongoTemplate.aggregate(aggregation, "trapezoids", Trapezoid.class).getMappedResults();
            trapezoids.forEach(t -> trapezoidIds.add(t.getId()));
        }

        return trapezoidIds;
    }

    @Override
    public List<AggregationOperation> findRealByLocation(List<double[]> locations) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.lookup("trapezoids", "trapezoid_info_id", "trapezoid_info_id", "trapezoid"));
        aggregationOperations.add(Aggregation.unwind("trapezoid"));
        aggregationOperations.add(Aggregation.match(Criteria.where("trapezoid._id").in(findAllTrapezoidIdByLocation(locations))));
        aggregationOperations.add(Aggregation.project("real_time", "update_time", "element_code", "trapezoid.area_code", "trapezoid.grid_code", "trapezoid.loc", "trapezoid.area_name").and("$real_info_id").concat("$trapezoid.grid_code").as("element_value_id"));
        return aggregationOperations;
    }

    public List<ObjectId> findAllTrapezoidIdByLocation(List<double[]> locations){
        List<ObjectId> trapezoidIds = new ArrayList<>();
        for (double[] loc : locations) {
            if (StringUtils.isEmpty(loc)) {
                continue;
            }

            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            aggregationOperations.add(Aggregation.geoNear(NearQuery.near(loc[0], loc[1]).spherical(true), "distance"));
            aggregationOperations.add(Aggregation.limit(1));
            aggregationOperations.add(Aggregation.project("_id"));

            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            List<Trapezoid> trapezoids = mongoTemplate.aggregate(aggregation, "trapezoids", Trapezoid.class).getMappedResults();
            trapezoids.forEach(t -> trapezoidIds.add(t.getId()));
        }

        return trapezoidIds;
    }
}
