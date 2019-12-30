package com.pingchuan.providermysql.controller;

import com.pingchuan.domain.User;
import com.pingchuan.providermysql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAll(){
        return userService.findAll();
    }

    @RequestMapping("/insert")
    public void insert(@RequestBody User user){
        userService.insert(user);
    }

    @RequestMapping("/deleteById")
    public void deleteById(@RequestBody int id){
        userService.deleteById(id);
    }

    @RequestMapping("/updateById")
    public void updateById(@RequestBody User user){
        userService.updateById(user);
    }
}
