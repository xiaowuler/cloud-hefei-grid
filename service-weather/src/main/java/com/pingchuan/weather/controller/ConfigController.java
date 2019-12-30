package com.pingchuan.weather.controller;

import com.pingchuan.domain.Config;
import com.pingchuan.weather.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public List<Config> findAll(){
        return configService.findAll();
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insert(String name, String value, String description){
        Config config = new Config();
        config.setName(name);
        config.setValue(value);
        config.setDescription(description);
        configService.insert(config);
    }

    @RequestMapping(value = "/updateById",method = RequestMethod.POST)
    public void updateById(int id, String name, String value, String description){
        Config config = new Config();
        config.setId(id);
        config.setName(name);
        config.setValue(value);
        config.setDescription(description);
        configService.updateByiId(config);
    }
}
