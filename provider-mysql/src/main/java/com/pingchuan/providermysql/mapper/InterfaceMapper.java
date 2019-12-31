package com.pingchuan.providermysql.mapper;

import java.util.Date;
import java.util.List;

import com.pingchuan.domain.Interface;
import com.pingchuan.dto.web.InterfaceDTO;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InterfaceMapper {
    Interface findOneById(int id);
    List<Interface> findAll();
    List<InterfaceDTO> findInterfacesByRequestTime(@Param("requestStartTime") Date requestStartTime, @Param("requestEndTime") Date requestEndTime);
}