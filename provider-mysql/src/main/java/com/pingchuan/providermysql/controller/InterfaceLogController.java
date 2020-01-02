package com.pingchuan.providermysql.controller;

import java.util.Date;
import com.pingchun.utils.PageResult;
import com.pingchuan.domain.InterfaceLog;
import com.pingchuan.dto.web.InterfaceLogDTO;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.pingchuan.providermysql.service.InterfaceLogService;

@RestController
@RequestMapping("interfaceLog")
public class InterfaceLogController {

    @Autowired
    private InterfaceLogService interfaceLogService;

    @PostMapping("/insertOne")
    public void insertOne(@RequestBody InterfaceLog interfaceLog){
        interfaceLogService.insertOne(interfaceLog);
    }

    @PostMapping("findAllByPage")
    public PageResult<InterfaceLogDTO> findAllByPage(@RequestParam String interfaceName, @RequestParam String department, @RequestParam Date requestStartTime, @RequestParam Date requestEndTime, @RequestParam String state, @RequestParam int page, @RequestParam int rows){
        return interfaceLogService.findAllByPage(interfaceName, department, requestStartTime, requestEndTime, state, page, rows);
    }
}