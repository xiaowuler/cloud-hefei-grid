package com.pingchuan.weather.service;

import java.util.List;
import com.pingchuan.domain.Caller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "provider-mysql")
public interface CallerService {
    @PostMapping("caller/findDepartment")
    List<Caller> findDepartment();
}