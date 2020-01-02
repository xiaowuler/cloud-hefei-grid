package com.pingchuan.weather.service;

import java.util.Date;
import com.pingchun.utils.PageResult;
import com.pingchuan.dto.web.InterfaceLogDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider-mysql")
public interface InterfaceLogService  {

    @PostMapping("interfaceLog/findAllByPage")
    PageResult<InterfaceLogDTO> findAllByPage(@RequestParam String interfaceName, @RequestParam String department, @RequestParam Date requestStartTime, @RequestParam Date requestEndTime, @RequestParam String state, @RequestParam int page, @RequestParam int rows);
}