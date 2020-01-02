package com.pingchuan.providermysql.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import com.pingchuan.domain.InterfaceLog;
import com.pingchuan.dto.web.InterfaceLogDTO;

@Mapper
public interface InterfaceLogMapper {
    void insertOne(@Param("interfaceLog") InterfaceLog interfaceLog);
    int findTotalCount(@Param("interfaceName") String interfaceName, @Param("department") String department, @Param("requestStartTime") Date requestStartTime, @Param("requestEndTime") Date requestEndTime, @Param("state") String state);
    List<InterfaceLogDTO> findAllByPage(@Param("interfaceName") String interfaceName, @Param("department") String department, @Param("requestStartTime") Date requestStartTime, @Param("requestEndTime") Date requestEndTime, @Param("state") String state, @Param("pagination") int pagination, @Param("pageSize") int pageSize);
}