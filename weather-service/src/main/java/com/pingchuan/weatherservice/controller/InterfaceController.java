package com.pingchuan.weatherservice.controller;

import com.pingchuan.domain.Interface;
import com.pingchuan.weatherservice.service.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("interface")
public class InterfaceController {

    @Autowired
    private InterfaceService interfaceService;

    @RequestMapping("/findAll")
    public List<Interface> findAll() {
        return interfaceService.findAll();
    }

}
