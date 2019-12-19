package com.pingchuan.mongodb.dao.impl;

import com.pingchuan.model.Station;
import com.pingchuan.mongodb.dao.StationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StationDaoImpl implements StationDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Station> getAllByStations(List<String> stations) {

        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        aggregationOperations.add(Aggregation.match(Criteria.where("_id").in(stations)));
        aggregationOperations.add(Aggregation.project("loc"));

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Station> station = mongoTemplate.aggregate(aggregation, "stations", Station.class).getMappedResults();

        return station;
    }
}
