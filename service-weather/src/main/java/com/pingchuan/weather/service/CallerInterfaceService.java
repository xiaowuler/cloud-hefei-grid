package com.pingchuan.weather.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider-mysql")
public interface CallerInterfaceService {

    @PostMapping("callerInterface/addCallerInterface")
    void addCallerInterface(@RequestParam String code, @RequestParam Integer[] interfaceIds);

    @PostMapping("callerInterface/updateCallerInterface")
    void updateCallerInterface(@RequestParam String code, @RequestParam Integer[] interfaceIds);
}