package com.pingchuan.providermysql.mapper;

import java.util.List;
import com.pingchuan.domain.SystemSetting;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemSettingMapper {

    int getSystemSettingValueById(int id);
    int findTotalCount();
    List<SystemSetting> findAllByPage(@Param("page") int page, @Param("rows") int rows);
    void updateValueById(@Param("id") int id, @Param("value") int value);
}