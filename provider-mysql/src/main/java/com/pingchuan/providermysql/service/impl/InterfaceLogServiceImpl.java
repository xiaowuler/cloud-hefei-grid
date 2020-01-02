package com.pingchuan.providermysql.service.impl;

import java.util.List;
import java.util.Date;

import com.pingchun.utils.PageResult;
import com.pingchuan.domain.InterfaceLog;
import com.pingchuan.dto.web.InterfaceLogDTO;
import com.pingchuan.providermysql.mapper.InterfaceLogMapper;
import com.pingchuan.providermysql.service.InterfaceLogService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InterfaceLogServiceImpl implements InterfaceLogService {

    @Autowired
    private InterfaceLogMapper interfaceLogMapper;

    @Override
    public void insertOne(InterfaceLog interfaceLog) {
        interfaceLogMapper.insertOne(interfaceLog);
    }

    @Override
    public PageResult<InterfaceLogDTO> findAllByPage(String interfaceName, String department, Date requestStartTime, Date requestEndTime, String state, int page, int rows) {
        int pagination = (page - 1) * rows;
        int totalCount = findTotalCount(interfaceName, department, requestStartTime, requestEndTime, state);
        List<InterfaceLogDTO> dtos = interfaceLogMapper.findAllByPage(interfaceName, department, requestStartTime, requestEndTime, state, pagination, rows);

        PageResult<InterfaceLogDTO> pageResult = new PageResult<>();
        pageResult.setTotal(totalCount);
        pageResult.setRows(dtos);
        return pageResult;
    }

    @Override
    public int findTotalCount(String interfaceName, String department, Date requestStartTime, Date requestEndTime, String state) {
        return interfaceLogMapper.findTotalCount(interfaceName, department, requestStartTime, requestEndTime, state);
    }
}
