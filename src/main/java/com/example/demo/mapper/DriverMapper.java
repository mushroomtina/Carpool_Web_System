package com.example.demo.mapper;

import com.example.demo.domain.Driver;
import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface DriverMapper {
    Integer getDriverId(User user);
    void setDriverId(User user);
    void addDriver(Driver driver);
    Driver getDriverInfo(String license);

}
