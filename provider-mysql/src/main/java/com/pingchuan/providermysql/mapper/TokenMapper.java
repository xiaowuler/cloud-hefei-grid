package com.pingchuan.providermysql.mapper;

import com.pingchuan.domain.Token;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TokenMapper {

    void insertOne(@Param("token") Token token);

    Token findOneByCallerCode(@Param("code") String code);

    void deleteOneByCallerCode(@Param("code") String code);
}
