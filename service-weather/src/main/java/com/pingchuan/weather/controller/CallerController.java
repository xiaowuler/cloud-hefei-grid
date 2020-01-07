package com.pingchuan.weather.controller;

import java.util.List;
import com.pingchuan.domain.Caller;
import com.pingchuan.dto.web.CallerDTO;
import com.pingchuan.weather.service.CallerService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("caller")
public class CallerController {

    @Autowired
    private CallerService callerService;

    @PostMapping("findDepartment")
    public List<Caller> findDepartment(){
        return callerService.findDepartment();
    }

    @PostMapping("findCallerAuthorizationInfo")
    public List<CallerDTO> findCallerAuthorizationInfo(int page, int rows){
        return callerService.findCallerAuthorizationInfo(page, rows);
    }
}