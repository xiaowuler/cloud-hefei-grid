package com.pingchuan.weather.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.pingchuan.domain.Caller;
import com.pingchuan.dto.web.CallerDTO;
import com.pingchuan.weather.service.CallerService;

import com.pingchun.utils.PageResult;
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

    @PostMapping("findCodeAndLoginName")
    List<Caller> findCodeAndLoginName(){
        return callerService.findCodeAndLoginName();
    }

    @PostMapping("findCallerAuthorizationInfo")
    public PageResult<CallerDTO> findCallerAuthorizationInfo(int page, int rows){
        return callerService.findCallerAuthorizationInfo(page, rows);
    }

    @PostMapping("findAllByPage")
    public PageResult<Caller> findAllByPage(int page, int rows){
        return callerService.findAllByPage(page, rows);
    }

    @PostMapping("setCallerEnabled")
    public void setCallerEnabled(String code, int isEnabled){
        callerService.setCallerEnabled(code, isEnabled);
    }

    @PostMapping("addCaller")
    public void addCaller(String department, String loginName, String realName, String loginPassword, String role, int enabled){
        String code = UUID.randomUUID().toString().replace("-", "");
        Date updateTime = new Date();
        Caller caller = new Caller(code, department, loginName, realName, loginPassword, role, updateTime, enabled);
        callerService.addCaller(caller);
    }

    @PostMapping("updateCaller")
    public void updateCaller(String code, String department, String loginName, String realName, String loginPassword, String role, int enabled){
        Caller caller = new Caller(code, department, loginName, realName, loginPassword, role, new Date(), enabled);
        callerService.updateCaller(caller);
    }

    @PostMapping("deleteCaller")
    public void deleteCaller(String code){
        callerService.deleteCaller(code);
    }

    @PostMapping("isExistLoginName")
    public int isExistLoginName(String loginName){
        return callerService.isExistLoginName(loginName);
    }
}