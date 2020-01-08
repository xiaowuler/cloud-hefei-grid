package com.pingchuan.providermysql.service.impl;

import java.util.List;
import com.pingchuan.domain.Caller;
import com.pingchuan.dto.web.CallerDTO;
import com.pingchuan.providermysql.mapper.CallerMapper;
import com.pingchuan.providermysql.service.CallerService;
import com.pingchuan.providermysql.mapper.CallerInterfaceMapper;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CallerServiceImpl implements CallerService {

    @Autowired
    private CallerMapper callerMapper;

    @Autowired
    private CallerInterfaceMapper callerInterfaceMapper;

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

    @Override
    public List<Caller> findAllByPage(int page, int rows) {
        return callerMapper.findAllByPage((page - 1) * rows, rows);
    }

    @Override
    public void setCallerEnabled(String code, int isEnabled) {
        callerMapper.setCallerEnabled(code, isEnabled);
    }

    @Override
    public void addCaller(Caller caller) {
        callerMapper.addCaller(caller);
    }

    @Override
    public void updateCaller(Caller caller) {
        callerMapper.updateCaller(caller);
    }

    @Override
    public void deleteCaller(String code) {
        callerMapper.deleteCaller(code);
        callerInterfaceMapper.deleteCallerInterface(code);
    }
}