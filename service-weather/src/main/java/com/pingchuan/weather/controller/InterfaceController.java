package com.pingchuan.weather.controller;

import java.util.List;
import com.pingchuan.domain.Interface;
import com.pingchuan.dto.web.InterfaceAnalysisRateDTO;
import com.pingchuan.weather.service.InterfaceService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("interface")
public class InterfaceController {

    @Autowired
    private InterfaceService interfaceService;

    @PostMapping("getInterfaceBaseInfo")
    public List<InterfaceAnalysisRateDTO> getInterfaceBaseInfo(){
        return interfaceService.getInterfaceBaseInfo();
    }

    @PostMapping("getInterfaces")
    public List<Interface> getInterfaces(){
        return interfaceService.getInterfaces();
    }
}