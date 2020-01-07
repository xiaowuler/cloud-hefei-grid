package com.pingchuan.providermysql.service.impl;

import java.util.List;
import com.pingchuan.domain.CallerInterface;
import com.pingchuan.providermysql.mapper.CallerInterfaceMapper;
import com.pingchuan.providermysql.service.CallerInterfaceService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CallerInterfaceServiceImpl implements CallerInterfaceService {

    @Autowired
    private CallerInterfaceMapper callerInterfaceMapper;

    @Override
    public CallerInterface findOneByCallerAndInterface(String callerCode, Integer interfaceId) {
        return callerInterfaceMapper.findOneByCallerAndInterface(callerCode, interfaceId);
    }

    @Override
    public void addCallerInterface(String code, List<Integer> interfaceIds) {
        addCallerInterfaces(code, interfaceIds);
    }

    @Override
    public void updateCallerInterface(String code, List<Integer> interfaceIds) {
        callerInterfaceMapper.deleteCallerInterface(code);
        addCallerInterfaces(code, interfaceIds);
    }

    private void addCallerInterfaces(String code, List<Integer> interfaceIds){
        if (interfaceIds != null){
            for (Integer interfaceId: interfaceIds)
                callerInterfaceMapper.addCallerInterface(code, interfaceId);
        }
    }
}