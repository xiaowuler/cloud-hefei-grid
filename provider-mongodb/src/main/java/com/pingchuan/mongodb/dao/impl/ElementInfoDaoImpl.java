package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.contants.ForecastElementCode;
import com.pingchuan.model.ElementInfo;
import com.pingchuan.mongodb.dao.ElementInfoDao;
import com.pingchuan.mongodb.field.ElementField;
import com.pingchun.utils.TimeUtil;
import lombok.Data;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaowuler
 */

@Repository
public class ElementInfoDaoImpl implements ElementInfoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<AggregationOperation> findByUpdateTimeAndStartTimeAndElementCodeAndForecastModel(Date updateTime, Date startTime, String elementCode, String forecastModel) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.lookup("element_infos", "_id", "element_code", "element_info"));
        aggregationOperations.add(Aggregation.unwind("element_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("element_info.update_time").is(updateTime).and("element_info.start_time").is(startTime).and("element_info.forecast_model").is(forecastModel)));
        aggregationOperations.add(Aggregation.project(ElementField.elementInfoFields).and("element_info._id").as("element_info_id").andExclude("_id"));
        if (!StringUtils.isEmpty(elementCode)){
            aggregationOperations.add(Aggregation.match(Criteria.where("element_code").is(elementCode)));
        }

        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByForecastModel(String forecastModel, String elementCode) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.lookup("element_infos", "_id", "element_code", "element_info"));
        aggregationOperations.add(Aggregation.unwind("element_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("element_info.forecast_model").is(forecastModel)));
        aggregationOperations.add(Aggregation.project(ElementField.elementInfoFields).and("element_info._id").as("element_info_id").andExclude("_id"));
        if (!StringUtils.isEmpty(elementCode)){
            aggregationOperations.add(Aggregation.match(Criteria.where("element_code").is(elementCode)));
        }

        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findByForecastModelAndDate(String forecastModel, Date startTime, Date endTime) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.lookup("element_infos", "_id", "element_code", "element_info"));
        aggregationOperations.add(Aggregation.unwind("element_info"));
        aggregationOperations.add(Aggregation.match(Criteria.where("element_info.forecast_model").is(forecastModel).and("element_info.start_time").gte(startTime).lte(endTime)));
        aggregationOperations.add(Aggregation.project(ElementField.elementInfoFields).and("element_info._id").as("element_info_id").andExclude("_id"));

        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findOne(String elementCode, Date initialDate, String modeCode, String orgCode, Integer forecastInterval, Integer forecastLevel) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.match(Criteria.where("element_code").is(elementCode).and("initial_time").is(initialDate).and("mode_code").is(modeCode).and("org_code").is(orgCode).and("forecast_interval").is(forecastInterval).and("forecast_level").is(forecastLevel)));
        aggregationOperations.add(Aggregation.project(ElementField.elementInfoFields).and("_id").as("element_info_id").andExclude("_id"));
        aggregationOperations.add(Aggregation.limit(1));

        return aggregationOperations;
    }

    @Override
    public List<AggregationOperation> findOneById(List<Long> ids) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.match(Criteria.where("_id").in(ids)));
        aggregationOperations.add(Aggregation.project(ElementField.elementInfoFields).and("_id").as("element_info_id").andExclude("_id"));
        return aggregationOperations;
    }

    @Override
    public List<ElementInfo> findSevenDayInitialTimes() {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.match(Criteria.where("element_code").is("EDA10").and("mode_code").is("SPCC").and("org_code").is("BEHF").and("forecast_level").is(1010)));
        aggregationOperations.add(Aggregation.project("initial_time", "_id"));
        aggregationOperations.add(Aggregation.group("initial_time", "_id"));
        aggregationOperations.add(Aggregation.sort(Sort.Direction.DESC, "initial_time"));
        aggregationOperations.add(Aggregation.limit(20));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<ElementInfo> elements = mongoTemplate.aggregate(aggregation, "element_infos", ElementInfo.class).getMappedResults();
        return elements;
    }

    @Override
    public List<ElementInfo> findByInitialTime(Date initialTime) {
        List<AggregationOperation> aggregationOperations = new ArrayList<>();

        aggregationOperations.add(Aggregation.match(Criteria.where("element_code").in(ForecastElementCode.elementCodes).and("initial_time").is(initialTime).and("mode_code").is("SPCC").and("org_code").is("BEHF").and("forecast_level").is(1010)));
        aggregationOperations.add(Aggregation.project("_id", "initial_time", "trapezoid_info_id"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<ElementInfo> elements = mongoTemplate.aggregate(aggregation, "element_infos", ElementInfo.class).getMappedResults();
        return elements;
    }
}
