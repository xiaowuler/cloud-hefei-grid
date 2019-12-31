package com.pingchuan.providermysql.controller;

import java.util.List;
import com.pingchuan.domain.Interface;
import com.pingchuan.dto.web.InterfaceAnalysisRateDTO;
import com.pingchuan.providermysql.service.InterfaceService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("interface")
public class InterfaceController {

    @Autowired
    private InterfaceService interfaceService;

    @PostMapping("findOneById")
    public Interface findOneById(int id){
        return interfaceService.findOneById(id);
    }

    @PostMapping("findAll")
    public List<Interface> findAll(){
        return interfaceService.findAll();
    }

    @CrossOrigin
    @PostMapping("getInterfaceBaseInfo")
    public List<InterfaceAnalysisRateDTO> getInterfaceBaseInfo(){
        return interfaceService.getInterfaceBaseInfo();
    }
}