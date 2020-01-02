package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.CallerInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CallerInterfaceMapper {
    CallerInterface findOneByCallerAndInterface(@Param("callerCode") String callerCode, @Param("interfaceId") Integer interfaceId);
}