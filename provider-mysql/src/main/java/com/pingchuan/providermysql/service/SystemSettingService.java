package com.pingchuan.providermysql.service;

import com.pingchun.utils.PageResult;
import com.pingchuan.domain.SystemSetting;

public interface SystemSettingService {

    int findTotalCount();
    PageResult<SystemSetting> findAllByPage(int pageIndex, int rows);
    void updateValueById(int id, int value);
}