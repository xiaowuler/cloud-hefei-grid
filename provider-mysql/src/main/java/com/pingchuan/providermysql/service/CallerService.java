package com.pingchuan.providermysql.service;

import java.util.List;
import com.pingchuan.domain.Caller;
import com.pingchuan.dto.web.CallerDTO;

public interface CallerService {

    Caller findOneByUsernameAndPassword(String username, String password);
    List<Caller> findDepartment();
    List<CallerDTO> findCallerAuthorizationInfo(int page, int rows);
    List<Caller> findAllByPage(int page, int rows);
    void setCallerEnabled(String code, int isEnabled);
    void addCaller(Caller caller);
    void updateCaller(Caller caller);
    void deleteCaller(String code);
    int isExistLoginName(String loginName);
}