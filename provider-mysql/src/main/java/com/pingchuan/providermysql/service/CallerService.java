package com.pingchuan.providermysql.service;

import com.pingchuan.domain.Caller;

import java.util.List;

public interface CallerService {
    Caller findOneByUsernameAndPassword(String username, String password);
    List<Caller> findDepartment();
}