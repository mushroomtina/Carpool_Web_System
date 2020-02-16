package com.example.demo.service.impl;

import com.example.demo.domain.Driver;
import com.example.demo.domain.Order;
import com.example.demo.mapper.DriverMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.domain.User;
import com.example.demo.service.DriversService;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriversServiceImpl implements DriversService {
    @Autowired
    private DriverMapper driverMapper;

    @Override
    public Integer getDriverId(User user){
        return driverMapper.getDriverId(user);
    }

    @Override
    public void addDriver(Driver driver, User user){
        driverMapper.addDriver(driver);
        Driver driverInfo = driverMapper.getDriverInfo(driver.getLicense());
        user.setDr_id(driverInfo.getId());
        driverMapper.setDriverId(user);
    }
}
