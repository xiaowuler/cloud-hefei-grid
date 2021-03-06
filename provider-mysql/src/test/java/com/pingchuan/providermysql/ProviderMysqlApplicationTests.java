package com.pingchuan.providermysql;

import com.pingchuan.domain.SystemSetting;
import com.pingchuan.dto.web.InterfaceDTO;
import com.pingchuan.providermysql.mapper.InterfaceMapper;
import com.pingchuan.providermysql.service.InterfaceService;
import com.pingchuan.providermysql.service.impl.InterfaceServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ProviderMysqlApplicationTests {

    @Autowired
    private InterfaceMapper interfaceMapper;

    @Autowired
    private InterfaceService interfaceService;

    @Test
    void contextLoads() {

    }
}