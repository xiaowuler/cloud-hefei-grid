package com.pingchuan.weather.controller;

import com.pingchun.utils.PageResult;
import com.pingchuan.domain.SystemSetting;
import com.pingchuan.weather.service.SystemSettingService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("systemSetting")
public class SystemSettingController {

    @Autowired
    private SystemSettingService settingService;

    @PostMapping("findAllByPage")
    public PageResult<SystemSetting> findAllByPage(int page, int rows){
        return settingService.findAllByPage(page, rows);
    }

    @PostMapping("updateValueById")
    public void updateValueById(int id, int value){
        settingService.updateValueById(id, value);
    }
}