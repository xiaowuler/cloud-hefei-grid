package com.pingchuan.weather.service;

import com.pingchuan.domain.Config;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "provider-mysql")
public interface ConfigService {

    @RequestMapping(value = "config/findAll",method = RequestMethod.POST)
    List<Config> findAll();

    @RequestMapping(value = "config/insert",method = RequestMethod.POST)
    void insert(Config config);

    @RequestMapping(value = "config/updateById",method = RequestMethod.POST)
    void updateByiId(Config config);

}
