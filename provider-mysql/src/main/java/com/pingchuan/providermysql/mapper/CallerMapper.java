package com.pingchuan.providermysql.mapper;

import java.util.List;
import com.pingchuan.domain.Caller;
import com.pingchuan.dto.web.CallerDTO;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CallerMapper {
    Caller findOneByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    List<Caller> findDepartment();
    List<CallerDTO> findCallerAuthorizationInfo(@Param("page") int page, @Param("rows") int rows);
}