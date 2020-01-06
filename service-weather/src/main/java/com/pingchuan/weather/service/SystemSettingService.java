package com.pingchuan.weather.service;

import com.pingchun.utils.PageResult;
import com.pingchuan.domain.SystemSetting;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider-mysql")
public interface SystemSettingService {

    @PostMapping("systemSetting/findAllByPage")
    PageResult<SystemSetting> findAllByPage(@RequestParam int page, @RequestParam int rows);

    @PostMapping("systemSetting/updateValueById")
    void updateValueById(@RequestParam int id, @RequestParam int value);
}