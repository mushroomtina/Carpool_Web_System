package com.example.demo.mapper;

import com.example.demo.domain.Driver;
import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface OrderMapper {
    void addRequest(Order order);
    List<Order> getPassengerOrder(Integer id);
    void deletePassengerOrder(Integer id);
    List<Order> getDriverOrder(Integer id);
    void addCarpool(Order order);
    List<Order> getPassengerOrderByCpId(Integer id);
    Order getCarpoolOrderByCpId(Integer id);
    List<Order> getSatisfied(Order order);
    void addPassengerToCarpool(Integer id,Integer CpId);
    void delPassengerFromCarpool(Integer id);
    Integer getCarpoolCap(Integer id);
    Integer getCurrentCap(Integer id);
}
