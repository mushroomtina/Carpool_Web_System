package com.example.demo.controller;

import com.example.demo.domain.Driver;
import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.service.DriversService;
import com.example.demo.service.OrdersService;
import com.example.demo.service.UsersService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class controller1 {
    @Resource
    private UsersService usersService;
    @Resource
    private DriversService driversService;
    @Resource
    private OrdersService ordersService;
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/loginuser")
    public String queryUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpServletRequest request){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User userInfo = usersService.checkUser(user);
        if(userInfo!=null){
            request.getSession().setAttribute("userInfo",userInfo);
            return "redirect:/driverOrPassenger";
        }
        return "redirect:/";
    }

    @PostMapping("/signupuser")
    public String insertUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("tel") String telephone,
                             @RequestParam("email") String email,
                             HttpServletRequest request){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setTel(telephone);
        user.setEmail(email);
        usersService.insertUser(user);
        User userInfo = usersService.getUser(username);
        request.getSession().setAttribute("userInfo",userInfo);
        return "redirect:/driverOrPassenger";
    }
    @GetMapping("/driverOrPassenger")
    public String decisionType(){
        return "driverOrPassenger";
    }

    @GetMapping("/checkDriver")
    public String checkDriver(HttpServletRequest request){
        User userInfo = (User)request.getSession().getAttribute("userInfo");
        Integer driverId = driversService.getDriverId(userInfo);
        if(driverId==null){
            return "addDriverInfo";
        }else{
            return "redirect:/mainDriver";
        }
    }
    @PostMapping("/addDriverInfo")
    public String addDriver(@RequestParam("name") String name,
                            @RequestParam("license") String license,
                            @RequestParam("age") Integer age,
                            @RequestParam("gender") String gender,
                            @RequestParam("plate") String plate,
                            @RequestParam("brand") String brand,
                            @RequestParam("model") String model,
                            HttpServletRequest request){
        Driver driver = new Driver();
        driver.setName(name);
        driver.setLicense(license);
        driver.setAge(age);
        driver.setGender(gender);
        driver.setPlate(plate);
        driver.setBrand(brand);
        driver.setModel(model);
        User userInfo = (User)request.getSession().getAttribute("userInfo");
        driversService.addDriver(driver,userInfo);
        return "redirect:/mainDriver";
    }
    @GetMapping("/mainDriver")
    public String mainDriver(Model model,HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        List<Order> driverOrders = ordersService.getDriverOrder(userInfo.getId());
        model.addAttribute("driverOrders",driverOrders);
        return "mainDriver";
    }

    @PostMapping("/mainDriver")
    public String mainDriver2(@RequestParam("start") String start,
                                       @RequestParam("end") String end,
                                       @RequestParam("date") String date,
                                       @RequestParam("starttime") String time,
                                       @RequestParam("numbers") Integer person,
                                       HttpServletRequest request) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date datetime = formatter.parse(date + " " + time);
        Order order = new Order();
        order.setOrigin(start);
        order.setDestination(end);
        order.setTime(datetime);
        order.setPerson(person);
        User userInfo = (User)request.getSession().getAttribute("userInfo");
        order.setUr_id(usersService.getUser(userInfo.getUsername()).getId());
        ordersService.addCarpool(order);
        return "redirect:/mainDriver";
    }
    @GetMapping("/showOrder")
    public String showOrderMatchDriverRoute(Model model, Integer orderId){
        List<Order> passengerOrderByCpId = ordersService.getPassengerOrderByCpId(orderId);
        model.addAttribute("currentPassengers",passengerOrderByCpId);
        model.addAttribute("cpid",orderId);
        return "/choosePassenger";
    }
    @GetMapping("/showRecommended")
    public String showRecommanded(Model model, Integer id) throws ParseException, com.vividsolutions.jts.io.ParseException, IOException {
        List<Order> orders = ordersService.showRecommanded(id);
        model.addAttribute("rcmOrders",orders);
        model.addAttribute("cpId",id);
        return "/showRecommendedPage";
    }

    @GetMapping("/addPsg2Cp")
    public String addPsg2Cp(Model model, Integer id,Integer cpId){
        if(ordersService.checkPassengerCap(cpId)){
            ordersService.addPassengerToCarpool(id,cpId);
            model.addAttribute("message","Congratulations! The passenger "+ id + " has been successfully added to your order.");
            return "showMsg";
        }
        model.addAttribute("message","The capability of passengers in this order exceeds the limit.");
        return "showMsg";

    }

    @GetMapping("/delPsgFromCp")
    public String delPsgFromCp(Model model, Integer id,Integer cpId){
        ordersService.delPassengerFromCarpool(id);
        model.addAttribute("message","The passenger "+ id + " has been successfully removed from your order.");
        return "showMsg";
    }

    @GetMapping("/mainPassenger")
    public String mainPsg(Model model,HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        return "mainPassenger";
    }
    @PostMapping("/mainPassenger")
    public String mainPsg2(@RequestParam("start") String start,
                                       @RequestParam("end") String end,
                                       @RequestParam("date") String date,
                                       @RequestParam("starttime") String time,
                                       @RequestParam("numbers") Integer person,
                                       HttpServletRequest request) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date datetime = formatter.parse(date + " " + time);
        Order order = new Order();
        order.setOrigin(start);
        order.setDestination(end);
        order.setTime(datetime);
        order.setPerson(person);
        User userInfo = (User)request.getSession().getAttribute("userInfo");
        order.setUr_id(usersService.getUser(userInfo.getUsername()).getId());
        ordersService.addRequest(order);
        return "redirect:/mainPassenger";
    }
//Save the user login info using session
    @GetMapping("/order")
    public String myOrder(Model model,HttpServletRequest request){
        return "myOrderPassenger";
    }

    @GetMapping("order/cancel")
    public String deleteOrder(Integer id){
        return "redirect:/order";
    }

    @GetMapping("/orderPassenger")
    public String myOrderPsg(Model model,HttpServletRequest request){
        User userInfo = (User) request.getSession().getAttribute("userInfo");
        List<Order> orderByUser = ordersService.getPassengerOrder(userInfo.getId());
        model.addAttribute("userInfo",userInfo);
        model.addAttribute("myOrders",orderByUser);
        return "myOrderPassenger";
    }

    @GetMapping("orderPassenger/cancel")
    public String myOrderPsgCcl(Integer id){
        ordersService.deletePassengerOrder(id);
        return "redirect:/orderPassenger";
    }
    @GetMapping("/setting")
    public String mySetting(){
        return "setting";
    }

    @GetMapping("/main/add")
    public String pickCar(Integer id,HttpServletRequest request){
        return "redirect:/main";
    }
}
