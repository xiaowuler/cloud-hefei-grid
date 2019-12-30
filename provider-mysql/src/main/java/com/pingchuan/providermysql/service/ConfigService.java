package com.pingchuan.providermysql.service;

import com.pingchuan.domain.Config;

import java.util.List;

public interface ConfigService {
    List<Config> findAll();
    void insert(Config config);
    void updateById(Config config);
}
