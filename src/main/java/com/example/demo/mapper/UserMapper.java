package com.example.demo.mapper;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Mapper
@Repository
public interface UserMapper {
    User getUser(String username);
    User checkUser(User user);
    void insertUser(User user);
}