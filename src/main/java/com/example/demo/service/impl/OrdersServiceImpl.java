package com.example.demo.service.impl;

import com.example.demo.domain.Order;
import com.example.demo.domain.Point;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.Algo.BufferAlgo;
import com.example.demo.service.OrdersService;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public void addRequest(Order order){
        orderMapper.addRequest(order);
    }

    @Override
    public List<Order> getPassengerOrder(Integer id){
        return orderMapper.getPassengerOrder(id);
    }

    @Override
    public void deletePassengerOrder(Integer id){
        orderMapper.deletePassengerOrder(id);
    }

    @Override
    public List<Order> getDriverOrder(Integer id){
        return orderMapper.getDriverOrder(id);
    }
    @Override
    public void addCarpool(Order order){
        orderMapper.addCarpool(order);
    }

    @Override
    public List<Order> getPassengerOrderByCpId(Integer id){
        return orderMapper.getPassengerOrderByCpId(id);
    }
    @Override
    public Order getCarpoolOrderByCpId(Integer id){
        return orderMapper.getCarpoolOrderByCpId(id);
    }
    @Override
    public List<Order> showRecommanded(Integer id) throws IOException, ParseException, com.vividsolutions.jts.io.ParseException {
//        get carpool info by cp_id
        Order carpoolOrderByCpId = orderMapper.getCarpoolOrderByCpId(id);
        Point startPoint = this.parseCoord(carpoolOrderByCpId.getOrigin());
        Point endPoint = this.parseCoord(carpoolOrderByCpId.getDestination());
        //create route
        BufferAlgo bufferAlgo = new BufferAlgo();
        bufferAlgo.setStartCoordinate(startPoint);
        bufferAlgo.setEndCoordinate(endPoint);
        bufferAlgo.setBufferDistance(1000);
        List<String> strings = bufferAlgo.routePlan();

        List<Order> satisfied = orderMapper.getSatisfied(carpoolOrderByCpId);
        Geometry bufferZone = bufferAlgo.createBufferZone(strings);
        List<Order> results = new ArrayList<>();
        for(Order order: satisfied){
            if((bufferAlgo.checkInZone(bufferZone,this.parseCoord(order.getOrigin())))
                &&(bufferAlgo.checkInZone(bufferZone,this.parseCoord(order.getDestination())))){
                results.add(order);
            }
        }
        return results;
    }

    public Point parseCoord(String coordStr){
        Point point = new Point();
        String[] splitedStr = coordStr.split(" ");
        point.setLatitude(Double.parseDouble(splitedStr[0]));
        point.setLongitude(Double.parseDouble(splitedStr[1]));
        return point;
    }

    @Override
    public void addPassengerToCarpool(Integer id,Integer CpId){
        orderMapper.addPassengerToCarpool(id,CpId);
    }

    @Override
    public void delPassengerFromCarpool(Integer id){
        orderMapper.delPassengerFromCarpool(id);
    }

    @Override
    public Boolean checkPassengerCap(Integer id){
        Integer carpoolCap = orderMapper.getCarpoolCap(id);
        Integer currentCap = orderMapper.getCurrentCap(id);
        return carpoolCap>currentCap;

    }
}
