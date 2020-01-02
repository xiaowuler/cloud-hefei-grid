package com.pingchuan.providermysql.mapper;

import java.util.List;
import com.pingchuan.domain.Caller;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CallerMapper {
    Caller findOneByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    List<Caller> findDepartment();
}