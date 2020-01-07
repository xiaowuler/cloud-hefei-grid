package com.pingchuan.weather.controller;

import java.util.ArrayList;
import java.util.List;
import com.pingchuan.weather.service.CallerInterfaceService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("callerInterface")
public class CallerInterfaceController {

    @Autowired
    private CallerInterfaceService callerInterfaceService;

    @PostMapping("addCallerInterface")
    public void addCallerInterface(@RequestParam(name = "code") String code, @RequestParam(name = "interfaceIds[]") Integer[] interfaceIds){
    callerInterfaceService.addCallerInterface(code, interfaceIds);
    }

    @PostMapping("updateCallerInterface")
    public void updateCallerInterface(@RequestParam String code, @RequestParam(name = "interfaceIds[]") Integer[] interfaceIds){
        callerInterfaceService.updateCallerInterface(code, interfaceIds);
    }
}