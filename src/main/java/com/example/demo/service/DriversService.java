package com.example.demo.service;

import com.example.demo.domain.Driver;
import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import org.springframework.stereotype.Service;

public interface DriversService {
    Integer getDriverId(User user);
    void addDriver(Driver driver,User user);

}
