package com.pingchuan.providermysql.service.impl;

import java.util.List;
import com.pingchuan.domain.Caller;
import com.pingchuan.dto.web.CallerDTO;
import com.pingchuan.providermysql.mapper.CallerMapper;
import com.pingchuan.providermysql.service.CallerService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CallerServiceImpl implements CallerService {

    @Autowired
    private CallerMapper callerMapper;

    @Override
    public Caller findOneByUsernameAndPassword(String username, String password) {
        return callerMapper.findOneByUsernameAndPassword(username, password);
    }

    @Override
    public List<Caller> findDepartment() {
        return callerMapper.findDepartment();
    }

    @Override
    public List<CallerDTO> findCallerAuthorizationInfo(int page, int rows) {
        return callerMapper.findCallerAuthorizationInfo((page - 1) * rows, rows);
    }
}