package com.pingchuan.providermysql.service;

import java.util.List;
import com.pingchuan.domain.CallerInterface;

public interface CallerInterfaceService {
    CallerInterface findOneByCallerAndInterface(String callerCode, Integer interfaceId);
    void addCallerInterface(String code, List<Integer> interfaceIds);
    void updateCallerInterface(String code, List<Integer> interfaceIds);
}
