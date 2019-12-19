package com.pingchuan.mongodb.dao;

import com.pingchuan.model.Station;

import java.util.List;

public interface StationDao {
    List<Station> getAllByStations(List<String> stations);
}
