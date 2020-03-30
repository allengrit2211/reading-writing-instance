package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;


@RestController
public class TestController {
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<User> reids() {
        return userService.queryUserList();
    }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public void saveUser() {
        User user = new User();
        user.setId(new Random().nextInt(1000));
        user.setName("Allen" + LocalDate.now().toString());
        userService.saveUser(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void update() {
        User user = new User();
        user.setId(20);
        user.setName("Allen-" + LocalDate.now().toString());
        userService.updateUser(user);
    }

}
