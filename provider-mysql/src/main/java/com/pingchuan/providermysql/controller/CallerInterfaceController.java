package com.pingchuan.providermysql.controller;

import java.util.List;
import com.pingchuan.domain.CallerInterface;
import com.pingchuan.providermysql.service.CallerInterfaceService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("callerInterface")
public class CallerInterfaceController {

    @Autowired
    private CallerInterfaceService callerInterfaceService;

    @PostMapping("/findOneByCallerAndInterface")
    public CallerInterface findOneByCallerAndInterface(String callerCode, Integer interfaceId){
        return callerInterfaceService.findOneByCallerAndInterface(callerCode, interfaceId);
    }

    @PostMapping("addCallerInterface")
    public void addCallerInterface(@RequestParam String code, @RequestParam Integer[] interfaceIds){
        callerInterfaceService.addCallerInterface(code, interfaceIds);
    }

    @PostMapping("updateCallerInterface")
    public void updateCallerInterface(@RequestParam String code, @RequestParam Integer[] interfaceIds){
        callerInterfaceService.updateCallerInterface(code, interfaceIds);
    }
}