package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.CallerInterface;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CallerInterfaceMapper {
    CallerInterface findOneByCallerAndInterface(@Param("callerCode") String callerCode, @Param("interfaceId") Integer interfaceId);
    void addCallerInterface(@Param("code") String code, @Param("interfaceId") int interfaceId);
    void deleteCallerInterface(@Param("code") String code);
}