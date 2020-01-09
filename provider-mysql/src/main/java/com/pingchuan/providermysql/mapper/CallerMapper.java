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
    List<Caller> findCodeAndLoginName();
    int findCallerAuthorizationCount();
    List<CallerDTO> findCallerAuthorizationInfo(@Param("page") int page, @Param("rows") int rows);
    int findTotalCount();
    List<Caller> findAllByPage(@Param("page") int page, @Param("rows") int rows);
    void setCallerEnabled(@Param("code") String code, @Param("isEnabled") int isEnabled);
    void addCaller(@Param("caller") Caller caller);
    void updateCaller(@Param("caller") Caller caller);
    void deleteCaller(@Param("code") String code);
    int isExistLoginName(@Param("loginName") String loginName);
    int isExistLoginNameByUpdate(@Param("code") String code, @Param("loginName") String loginName);
}