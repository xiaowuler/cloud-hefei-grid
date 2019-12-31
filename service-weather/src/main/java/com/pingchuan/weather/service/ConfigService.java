package com.pingchuan.weather.service;

import java.util.List;
import com.pingchuan.domain.Config;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "provider-mysql")
public interface ConfigService {

    @RequestMapping(value = "config/findAll",method = RequestMethod.POST)
    List<Config> findAll();

    @RequestMapping(value = "config/insert",method = RequestMethod.POST)
    void insert(Config config);

    @RequestMapping(value = "config/updateById",method = RequestMethod.POST)
    void updateByiId(Config config);
}