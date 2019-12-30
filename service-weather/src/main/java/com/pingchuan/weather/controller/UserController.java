package com.pingchuan.weather.controller;

import com.pingchuan.domain.User;
import com.pingchuan.weather.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public List<User> findAll(){
        return userService.findAll();
    }

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insert(String name,String passWord,int enabled){
        User user=new User();
        user.setName(name);
        user.setPassWord(passWord);
        user.setEnabled(enabled);
        userService.insert(user);
    }

    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public void deleteById(int id){
        userService.deleteById(id);
    }

    @RequestMapping(value = "/updateById",method = RequestMethod.POST)
    public void updateById(int id,String name,String passWord,int enabled){
        User user=new User();
        user.setId(id);
        user.setName(name);
        user.setPassWord(passWord);
        user.setEnabled(enabled);
        userService.UpdateById(user);
    }
}
