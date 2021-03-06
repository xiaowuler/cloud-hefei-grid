package com.pingchuan.providermysql.controller;

import java.util.List;
import com.pingchuan.domain.Caller;
import com.pingchun.utils.PageResult;
import com.pingchuan.dto.web.CallerDTO;
import com.pingchuan.providermysql.service.CallerService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("caller")
public class CallerController {

    @Autowired
    private CallerService callerService;

    @PostMapping("/findOneByUsernameAndPassword")
    public Caller findOneByUsernameAndPassword(String username, String password){
        return callerService.findOneByUsernameAndPassword(username, password);
    }

    @PostMapping("findDepartment")
    public List<Caller> findDepartment(){
        return callerService.findDepartment();
    }

    @PostMapping("findCodeAndLoginName")
    public List<Caller> findCodeAndLoginName(){
        return callerService.findCodeAndLoginName();
    }

    @PostMapping("findCallerAuthorizationInfo")
    public PageResult<CallerDTO> findCallerAuthorizationInfo(@RequestParam int page, @RequestParam int rows){
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
    public void addCaller(@RequestBody Caller caller){
        callerService.addCaller(caller);
    }

    @PostMapping("updateCaller")
    public void updateCaller(@RequestBody Caller caller){
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

    @PostMapping("isExistLoginNameByUpdate")
    public int isExistLoginNameByUpdate(String code, String loginName){
        return callerService.isExistLoginNameByUpdate(code, loginName);
    }
}