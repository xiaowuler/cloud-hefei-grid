package com.pingchuan.providermysql.service.impl;

import java.util.List;
import com.pingchuan.domain.Caller;
import com.pingchun.utils.PageResult;
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
    public List<Caller> findCodeAndLoginName() {
        return callerMapper.findCodeAndLoginName();
    }

    @Override
    public PageResult<CallerDTO> findCallerAuthorizationInfo(int page, int rows) {
        PageResult<CallerDTO> pageResult = new PageResult<>();
        pageResult.setTotal(callerMapper.findCallerAuthorizationCount());
        pageResult.setRows(callerMapper.findCallerAuthorizationInfo((page - 1) * rows, rows));
        return pageResult;
    }

    @Override
    public PageResult<Caller> findAllByPage(int page, int rows) {
        PageResult<Caller> pageResult = new PageResult<>();
        pageResult.setTotal(callerMapper.findTotalCount());
        pageResult.setRows(callerMapper.findAllByPage((page - 1) * rows, rows));
        return pageResult;
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

    @Override
    public int isExistLoginName(String loginName) {
        return callerMapper.isExistLoginName(loginName);
    }
}