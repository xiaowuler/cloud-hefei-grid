package com.pingchuan.weather.service;

import java.util.List;
import com.pingchuan.domain.Caller;

import com.pingchuan.dto.web.CallerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider-mysql")
public interface CallerService {

    @PostMapping("caller/findDepartment")
    List<Caller> findDepartment();

    @PostMapping("caller/findCallerAuthorizationInfo")
    List<CallerDTO> findCallerAuthorizationInfo(@RequestParam int page, @RequestParam int rows);
}