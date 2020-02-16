package com.example.demo.service.impl;

import com.example.demo.domain.Order;
import com.example.demo.mapper.UserMapper;
import com.example.demo.domain.User;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUser(String username){
        return userMapper.getUser(username);
    }

    @Override
    public User checkUser(User loginInfo){
        return userMapper.checkUser(loginInfo);
    }

    @Override
    public void insertUser(User user){
        userMapper.insertUser(user);
    }


}
