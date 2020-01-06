package com.pingchuan.providermysql.service.impl;

import com.pingchun.utils.PageResult;
import com.pingchuan.domain.SystemSetting;

import com.pingchuan.providermysql.mapper.SystemSettingMapper;
import com.pingchuan.providermysql.service.SystemSettingService;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemSettingServiceImpl implements SystemSettingService {

    @Autowired
    private SystemSettingMapper settingMapper;

    @Override
    public int findTotalCount() {
        return settingMapper.findTotalCount();
    }

    @Override
    public PageResult<SystemSetting> findAllByPage(int pageIndex, int rows) {
        int page = (pageIndex - 1) * rows;
        PageResult<SystemSetting> pageResult = new PageResult<>();
        pageResult.setTotal(findTotalCount());
        pageResult.setRows(settingMapper.findAllByPage(page, rows));
        return pageResult;
    }

    @Override
    public void updateValueById(int id, int value) {
        settingMapper.updateValueById(id, value);
    }
}