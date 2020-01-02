package com.pingchuan.weather.controller;

import java.util.Date;
import com.pingchun.utils.PageResult;
import com.pingchuan.dto.web.InterfaceLogDTO;
import com.pingchuan.weather.service.InterfaceLogService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("interfaceLog")
public class InterfaceLogController {

    @Autowired
    private InterfaceLogService interfaceLogService;

    @PostMapping("findAllByPage")
    public PageResult<InterfaceLogDTO> findAllByPage(String interfaceName, String department, Date requestStartTime, Date requestEndTime, String state, int page, int rows){
        return interfaceLogService.findAllByPage(interfaceName, department, requestStartTime, requestEndTime, state, page, rows);
    }
}