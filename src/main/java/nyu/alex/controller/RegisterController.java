package nyu.alex.controller;


import net.sf.json.JSONObject;
import nyu.alex.dao.entity.Airline;
import nyu.alex.dao.entity.AirlineStaff;
import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Customer;
import nyu.alex.service.AirlineService;
import nyu.alex.service.RegisterService;
import nyu.alex.utils.serviceUtils.RegisterUtils;
import nyu.alex.utils.serviceUtils.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/register")
@CrossOrigin("http://localhost:3000/*")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private AirlineService airlineService;

    // Axios Grouping
    @GetMapping("/getAirlineList")
    @ResponseBody
    public List<String> getAirlineList(){
        List<Airline> allAirlines = airlineService.findAllAirlines();
        System.out.println(allAirlines);
        List<String> resultList = new ArrayList<>();
        for(Airline airline: allAirlines){
            resultList.add(airline.getAirlineName());
        }
        return resultList;
    }

    @GetMapping("/validateCustomer")
    @ResponseBody
    public Map<String, Boolean> validateCustomer(@RequestParam("email")String email){
        System.out.println("有请求过来了");
        Map<String, Boolean> stringBooleanMap = registerService.validateCustomer(email);
        return stringBooleanMap;
    }

    @GetMapping("/validateAirlineStaff")
    @ResponseBody
    public Map<String, Boolean> validateAirlineStaff(@RequestParam("username")String username){
        System.out.println("终于");
        Map<String, Boolean> stringBooleanMap = registerService.validateAirlineStaff(username);
        System.out.println(stringBooleanMap);
        return stringBooleanMap;
    }

    @GetMapping("/validateBookingAgent")
    @ResponseBody
    public Map<String, Boolean> validateBookingAgent(@RequestParam("email")String email){
        Map<String, Boolean> stringBooleanMap = registerService.validateBookingAgent(email);
        return stringBooleanMap;
    }

    @PostMapping("/registerCustomer")
    @ResponseBody
    public String registerCustomer(@RequestBody Customer customer){
        System.out.println(customer);
        registerService.registerCustomer(customer);
        System.out.println("注册成功");
        return "success";
    }

    @PostMapping("/registerAirlineStaff")
    @ResponseBody
    public String registerAirlineStaff(@RequestBody AirlineStaff airlineStaff){
        registerService.registerAirlineStaff(airlineStaff);
        System.out.println("注册成功");
        return "success";
    }

    @PostMapping("/registerBookingAgent")
    @ResponseBody
    public String registerBookingAgent(@RequestBody BookingAgent bookingAgent){
        registerService.registerBookingAgent(bookingAgent);
        System.out.println("注册成功");
        return "success";
    }




}
