package com.pingchuan.weather.service;

import java.util.List;
import com.pingchuan.domain.User;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "provider-mysql")
public interface UserService {

    @RequestMapping(value = "user/findAll",method = RequestMethod.POST)
    List<User> findAll();

    @RequestMapping(value = "user/insert",method = RequestMethod.POST)
    void insert(User user);

    @RequestMapping(value = "user/deleteById",method = RequestMethod.POST)
    void deleteById(int id);

    @RequestMapping(value = "user/updateById",method = RequestMethod.POST)
    void UpdateById(User user);
}