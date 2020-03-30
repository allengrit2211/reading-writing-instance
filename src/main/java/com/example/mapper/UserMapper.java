package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName: UserMapper
 * @description:
 * @author: Allen
 * @create: 2020-02-25 16:52
 **/
@Mapper
public interface UserMapper {

    List<User> queryUserList();

    List<User> selectUserList();

    List<User> findUserList();

    List<User> getUserList();

    void saveUser(User user);

    void updateUser(User user);


}
