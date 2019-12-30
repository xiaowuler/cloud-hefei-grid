package com.pingchuan.providermysql.service.impl;

import com.pingchuan.domain.User;
import com.pingchuan.providermysql.mapper.UserMapper;
import com.pingchuan.providermysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void deleteById(int id) {
        userMapper.deleteById(id);
    }

    @Override
    public void updateById(User user) {
        userMapper.updateById(user);
    }
}
