package com.pingchuan.providermysql.mapper;

import java.util.List;
import com.pingchuan.domain.CallerInterface;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CallerInterfaceMapper {
    CallerInterface findOneByCallerAndInterface(@Param("callerCode") String callerCode, @Param("interfaceId") Integer interfaceId);
    void addCallerInterface(@Param("code") String code, @Param("interfaceId") int interfaceId);
    void deleteCallerInterface(@Param("code") String code);
    List<Integer> findInterfaceIdByCode(@Param("code") String code);
}