package com.pingchuan.providermysql.service;

import java.util.Date;
import com.pingchuan.domain.InterfaceLog;
import com.pingchun.utils.PageResult;
import com.pingchuan.dto.web.InterfaceLogDTO;

public interface InterfaceLogService {
    void insertOne(InterfaceLog interfaceLog);
    int findTotalCount(String interfaceName, String department, Date requestStartTime, Date requestEndTime, String state);
    PageResult<InterfaceLogDTO> findAllByPage(String interfaceName, String department, Date requestStartTime, Date requestEndTime, String state, int pagination, int pageSize);
}