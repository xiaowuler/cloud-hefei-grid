package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.Config;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConfigMapper {
    List<Config> findAll();
    void insert(@Param("config")Config config);
    void updateById(@Param("config") Config config);
}
