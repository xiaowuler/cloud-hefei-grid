package com.pingchuan.providermysql.service.impl;

import java.util.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;

import com.pingchun.utils.TimeUtil;
import com.pingchuan.domain.Interface;
import com.pingchuan.dto.web.InterfaceDTO;
import com.pingchuan.dto.web.InterfaceAnalysisRateDTO;
import com.pingchuan.providermysql.mapper.InterfaceMapper;
import com.pingchuan.providermysql.service.InterfaceService;
import com.pingchuan.providermysql.mapper.SystemSettingMapper;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterfaceServiceImpl implements InterfaceService {

    @Autowired
    private InterfaceMapper interfaceMapper;

    @Autowired
    private SystemSettingMapper systemSettingMapper;

    @Override
    public Interface findOneById(int id) {
        return interfaceMapper.findOneById(id);
    }

    @Override
    public List<Interface> findAll() {
        return interfaceMapper.findAll();
    }

    @Override
    public List<InterfaceAnalysisRateDTO> getInterfaceBaseInfo() {
        int value = systemSettingMapper.getSystemSettingValueById(5);
        Date requestEndTime = new Date();
        Date requestStartTime = new Date(requestEndTime.getYear(), requestEndTime.getMonth(), requestEndTime.getDate() - value, 0, 0, 0);
        List<InterfaceDTO> interfaces = interfaceMapper.findInterfacesByRequestTime(requestStartTime, requestEndTime);
        return dealWithInterfaceInfo(interfaces);
    }

    private List<InterfaceAnalysisRateDTO> dealWithInterfaceInfo(List<InterfaceDTO> interfaces){
        List<InterfaceAnalysisRateDTO> interfaceAnalysisRateDTOS = new ArrayList<>();
        Date today = TimeUtil.getTodayZeroOClock();
        Date tomorrow = TimeUtil.addDay(today, 1);

        Date yesterday = TimeUtil.addDay(today, -1);
        Date dayBefore = TimeUtil.addDay(yesterday, -1);

        for (Interface info: interfaceMapper.findAll()){
            long successCount = interfaces.stream().filter(i -> i.getState() == 1 && i.getId() == info.getId()).count();
            long failureCount = interfaces.stream().filter(i -> i.getState() == 0 && i.getId() == info.getId()).count();
            long invokeCount = interfaces.stream().filter(i -> i.getId() == info.getId()).count();
            long totalMillisecond = interfaces.stream().filter(i -> i.getId() == info.getId() && i.getExecuteStartTime() != null && i.getRequestEndTime() != null).mapToLong(i -> i.getExecuteEndTime().getTime() - i.getExecuteStartTime().getTime()).sum();
            long successMillisecond = interfaces.stream().filter(i -> i.getId() == info.getId() && i.getState() == 1 && i.getExecuteStartTime() != null && i.getRequestEndTime() != null).mapToLong(i -> i.getExecuteEndTime().getTime() - i.getExecuteStartTime().getTime()).sum();
            long failureMillisecond = interfaces.stream().filter(i -> i.getId() == info.getId() && i.getState() == 0 && i.getExecuteStartTime() != null && i.getRequestEndTime() != null).mapToLong(i -> i.getExecuteEndTime().getTime() - i.getExecuteStartTime().getTime()).sum();

            long todayInvokeCount = interfaces.stream().filter(i -> i.getId() == info.getId() && i.getRequestStartTime().compareTo(new Timestamp(today.getTime())) != -1 && i.getRequestEndTime().compareTo(new Timestamp(tomorrow.getTime())) != 1).count();
            long yesterdayInvokeCount = interfaces.stream().filter(i -> i.getId() == info.getId() && i.getRequestStartTime().compareTo(new Timestamp(yesterday.getTime())) != -1 && i.getRequestEndTime().compareTo(new Timestamp(today.getTime())) != 1).count();
            long dayBeforeInvokeCount = interfaces.stream().filter(i -> i.getId() == info.getId() && i.getRequestStartTime().compareTo(new Timestamp(dayBefore.getTime())) != -1 && i.getRequestEndTime().compareTo(new Timestamp(yesterday.getTime())) != 1).count();

            DecimalFormat df = new DecimalFormat("#.00");
            double successRate = 0.0;
            double failureRate = 0.0;
            double averageTime = 0.0;
            double successAverageTime = 0.0;
            double failureAverageTime = 0.0;

            if (successCount != 0)
                successRate = Double.valueOf(df.format(Double.valueOf(successCount) / invokeCount * 100));

            if (failureCount != 0)
                failureRate = Double.valueOf(df.format(Double.valueOf(failureCount) / invokeCount * 100));

            if (totalMillisecond != 0)
                averageTime = Double.valueOf(df.format(Double.valueOf(totalMillisecond) / invokeCount));

            if (successMillisecond != 0)
                successAverageTime = Double.valueOf(df.format(Double.valueOf(successMillisecond) / successCount));

            if (failureMillisecond != 0)
                failureAverageTime = Double.valueOf(df.format(Double.valueOf(failureMillisecond) / failureCount));

            InterfaceAnalysisRateDTO dto = new InterfaceAnalysisRateDTO(info.getExplain(), successRate, failureRate, invokeCount, averageTime, dayBeforeInvokeCount, yesterdayInvokeCount, todayInvokeCount, successAverageTime, failureAverageTime);
            interfaceAnalysisRateDTOS.add(dto);
        }
        return interfaceAnalysisRateDTOS;
    }
}