package com.pingchuan.providermysql.service;

import java.util.List;
import com.pingchuan.domain.Caller;
import com.pingchuan.dto.web.CallerDTO;
import org.apache.ibatis.annotations.Param;

public interface CallerService {
    Caller findOneByUsernameAndPassword(String username, String password);
    List<Caller> findDepartment();
    List<CallerDTO> findCallerAuthorizationInfo(int page, int rows);
}