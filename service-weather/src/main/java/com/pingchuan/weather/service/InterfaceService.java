package com.pingchuan.weather.service;

import java.util.List;
import com.pingchuan.dto.web.InterfaceAnalysisRateDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "provider-mysql")
public interface InterfaceService {
    @PostMapping(value = "interface/getInterfaceBaseInfo")
    List<InterfaceAnalysisRateDTO> getInterfaceBaseInfo();
}