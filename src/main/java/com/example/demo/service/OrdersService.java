package com.example.demo.service;

import com.example.demo.domain.Order;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface OrdersService {
    void addRequest(Order order);
    List<Order> getPassengerOrder(Integer id);
    void deletePassengerOrder(Integer id);
    List<Order> getDriverOrder(Integer id);
    void addCarpool(Order order);
    List<Order> getPassengerOrderByCpId(Integer id);
    List<Order> showRecommanded(Integer id) throws IOException, ParseException, com.vividsolutions.jts.io.ParseException;
    Order getCarpoolOrderByCpId(Integer id);
    void addPassengerToCarpool(Integer id,Integer CpId);
    void delPassengerFromCarpool(Integer id);
    Boolean checkPassengerCap(Integer id);
}
