package com.pingchuan.providermysql.service;

import com.pingchuan.domain.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void insert(User user);
    void deleteById(int id);
    void updateById(User user);
}