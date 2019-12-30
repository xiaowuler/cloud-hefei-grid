package com.pingchuan.providermysql.controller;

import com.pingchuan.domain.Config;
import com.pingchuan.providermysql.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    public void insert(@RequestBody Config config){
        configService.insert(config);
    }

    @RequestMapping(value = "/updateById",method = RequestMethod.POST)
    public void updateById(@RequestBody Config config){
        configService.updateById(config);
    }
}
