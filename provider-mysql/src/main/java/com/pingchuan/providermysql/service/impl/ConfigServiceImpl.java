package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.Config;
import com.pingchuan.providermysql.mapper.ConfigMapper;
import com.pingchuan.providermysql.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public List<Config> findAll() {
        return configMapper.findAll();
    }

    @Override
    public void insert(Config config) {
        configMapper.insert(config);
    }

    @Override
    public void updateById(Config config) {
        configMapper.updateById(config);
    }
}
