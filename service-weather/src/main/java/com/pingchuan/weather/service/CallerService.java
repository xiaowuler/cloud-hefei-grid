package com.pingchuan.weather.service;

import java.util.List;
import com.pingchuan.domain.Caller;
import com.pingchun.utils.PageResult;
import com.pingchuan.dto.web.CallerDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "provider-mysql")
public interface CallerService {

    @PostMapping("caller/findDepartment")
    List<Caller> findDepartment();

    @PostMapping("caller/findCodeAndLoginName")
    List<Caller> findCodeAndLoginName();

    @PostMapping("caller/findCallerAuthorizationInfo")
    PageResult<CallerDTO> findCallerAuthorizationInfo(@RequestParam int page, @RequestParam int rows);

    @PostMapping("caller/findAllByPage")
    PageResult<Caller> findAllByPage(@RequestParam int page, @RequestParam int rows);

    @PostMapping("caller/setCallerEnabled")
    void setCallerEnabled(@RequestParam String code, @RequestParam int isEnabled);

    @RequestMapping(value = "caller/addCaller", method = RequestMethod.POST)
    void addCaller(Caller caller);

    @RequestMapping(value = "caller/updateCaller", method = RequestMethod.POST)
    void updateCaller(Caller caller);

    @RequestMapping(value = "caller/deleteCaller", method = RequestMethod.POST)
    void deleteCaller(@RequestParam String code);

    @RequestMapping(value = "caller/isExistLoginName", method = RequestMethod.POST)
    int isExistLoginName(@RequestParam String loginName);
}