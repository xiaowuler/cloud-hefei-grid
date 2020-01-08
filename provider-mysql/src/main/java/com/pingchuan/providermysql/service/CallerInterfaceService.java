package com.pingchuan.providermysql.service;

import com.pingchuan.domain.CallerInterface;

public interface CallerInterfaceService {
    CallerInterface findOneByCallerAndInterface(String callerCode, Integer interfaceId);
    void addCallerInterface(String code, Integer[] interfaceIds);
    void updateCallerInterface(String code, Integer[] interfaceIds);
}
