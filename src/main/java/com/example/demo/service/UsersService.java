package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;

import java.util.List;

public interface UsersService {
    User getUser(String username);
    User checkUser(User user);
    void insertUser(User user);
}
