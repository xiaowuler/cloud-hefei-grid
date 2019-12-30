package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    void insert(@Param("user") User user);
    void deleteById(int id);
    void updateById(@Param("user") User user);
}
