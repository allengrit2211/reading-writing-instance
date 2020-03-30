package com.example.service;

import com.example.entity.User;

import java.util.List;

/**
 * @ClassName: UserService
 * @description:
 * @author: Allen
 * @create: 2020-03-27 14:25
 **/
public interface UserService {

    List<User> queryUserList();


    void saveUser(User user);

    void updateUser(User user);
}
