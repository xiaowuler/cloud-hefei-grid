package com.pingchuan.providermysql.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemSettingMapper {

    int getSystemSettingValueById(int id);
}