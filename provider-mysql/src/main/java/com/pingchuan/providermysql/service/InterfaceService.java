package com.pingchuan.providermysql.service;

import java.util.List;
import com.pingchuan.domain.Interface;
import com.pingchuan.dto.web.InterfaceAnalysisRateDTO;

public interface InterfaceService {
    Interface findOneById(int id);
    List<Interface> findAll();
    List<InterfaceAnalysisRateDTO> getInterfaceBaseInfo();
}