package com.pingchuan.weather.controller;

import java.util.List;
import com.pingchuan.weather.service.CallerInterfaceService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("callerInterface")
public class CallerInterfaceController {

    @Autowired
    private CallerInterfaceService callerInterfaceService;

    @PostMapping("addCallerInterface")
    public void addCallerInterface(@RequestParam String code, @RequestParam List<Integer> interfaceIds){
        callerInterfaceService.addCallerInterface(code, interfaceIds);
    }

    @PostMapping("updateCallerInterface")
    public void updateCallerInterface(@RequestParam String code, @RequestParam List<Integer> interfaceIds){
        callerInterfaceService.updateCallerInterface(code, interfaceIds);
    }
}