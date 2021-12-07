package nyu.alex.controller;

import nyu.alex.dao.entity.*;
import nyu.alex.dao.mapper.IAirlineStaffDao;
import nyu.alex.dao.mapper.IBookingAgentDao;
import nyu.alex.dao.mapper.ICustomerDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.service.CustomerService;
import nyu.alex.utils.dataUtils.DataRow;
import nyu.alex.utils.dataUtils.FlightInfo;
import nyu.alex.utils.dataUtils.TicketInfo;
import nyu.alex.utils.serviceUtils.GrantUtils;
import nyu.alex.utils.serviceUtils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin(origins="http://localhost:3000",allowCredentials = "true")
@RequestMapping("/test")
public class TestController {
    @Resource
    private ICustomerDao customerDao;

    @Autowired
    private CustomerService customerService;


    @Resource
    private IFlightDao flightDao;

    @Resource
    private IBookingAgentDao bookingAgentDao;

    @Resource
    private IAirlineStaffDao airlineStaffDao;

    @GetMapping("/flights")
    @ResponseBody
    public String testFindAllFlight(){
        List<Flight> flights = flightDao.findAllFlights();
        for(Flight f: flights){
            System.out.println(f);
        }
        return "success";
    }


    @GetMapping("/airlineFlights")
    @ResponseBody
    public String testFindFlightsForAirline(){
        List<Flight> flights = flightDao.findAllFlightsForAirline("Cathay Pacific");
        for(Flight flight:flights){
            System.out.println(flight);
        }
        return "success";
    }

    @GetMapping("/flightsWithCustomer")
    @ResponseBody
    public String testAllFlightsWithCustomer(){
        Customer customer = customerDao.findAllFlightsWithCustomer("abababababa@gmail.com","China Eastern");
        List<Flight> flights= customer.getTakenFlights();
        System.out.println(flights);
        return "success";
    }

    @GetMapping("/insertAirplane")
    @ResponseBody
    public String testInsertNewAirplane(){
        Airplane airplane = new Airplane();
        airplane.setId("A550");
        airplane.setAirline("Spring Airlines");
        airplane.setSeats(450);
        airlineStaffDao.insertNewAirplane(airplane);
        return "success";
    }

    @GetMapping("/findAirlineStaff/{name}")
    @ResponseBody
    public String testFindAllAirlineStaffByName(@PathVariable("name") String name){
        AirlineStaff airlineStaff = airlineStaffDao.findAirlineStaffByName(name);
        System.out.println(airlineStaff);
        System.out.println(airlineStaff.getPermissionDescription());
        return "success";
    }

    @GetMapping("/findTopKBookingAgent")
    @ResponseBody
    public String testTopKBookingAgent(){
        List<BookingAgent> bookingAgents = airlineStaffDao.findTopKBookingAgentEachPeriod("YEAR", 5, 1);
        System.out.println(bookingAgents);
        return "success";
    }

    @GetMapping("/findFreqCustomer")
    @ResponseBody
    public String testFreqCustomer(){
        List<Customer> customers = airlineStaffDao.findAllCustomerPeriod("YEAR",1);
        System.out.println(customers);
        return "success";
    }

    @GetMapping("/findTicketNum")
    @ResponseBody
    public String testTicketNum(){
        int[] tmp = {2020,2021};
        List<TicketInfo> ticketsInfo = airlineStaffDao.findAmountOfTicketsEachPeriod(tmp);
        System.out.println(ticketsInfo);
        return "success";
    }

    @GetMapping("/findFlightInfo")
    @ResponseBody
    public String testFlightStatus(){
        List<FlightInfo> flightInfo = flightDao.findAllFlightStatus();
        System.out.println(flightInfo);
        return "success";
    }

    @GetMapping("/findCustomerFlight")
    @ResponseBody
    public String testCustomerFlights(){
        List<Flight> flights = customerService.findAllTakenFlights("abababababa@gmail.com", "China Eastern");
        System.out.println(flights);
        return "success";
    }

    @PostMapping("/grantTest")
    @ResponseBody
    public String testGrant(@RequestBody GrantUtils grantUtils){
        System.out.println(grantUtils.getPermission());
//        System.out.println(Arrays.toString(permission));
//        airlineStaffDao.grantAirlineStaff(userName,permission);
        return "success";
    }


    @PostMapping("/testLogin")
    @ResponseBody
    public String testLogin(@RequestBody LoginUtils loginUtils){
        System.out.println(loginUtils);
        return "success";
    }


    //测试RequestParam和ResponseBody的区别
    @PostMapping("/testParam")
    @ResponseBody
    public String testRequest(@RequestParam("name") String name,@RequestParam("city") String city){
            System.out.println("请求体中没有数据");
            System.out.println(name);
            System.out.println(city);
        return "success";
    }

    @PostMapping("/testJson")
    @ResponseBody
    public String testRequest(@RequestBody Airport airport,@RequestParam("type") String userType){
        System.out.println(userType);
        System.out.println("从请求体中获取数据");
        System.out.println(airport.getAirportName());
        System.out.println(airport.getAirportCity());
        return "success";
    }

    @PostMapping("/testAirline")
    @ResponseBody
    public List<Flight> testAirline(@RequestParam("airlineName") String airlineName){
        return flightDao.findAllFlightsForAirline("Spring Airlines");
    }


    // 用户权限认证
    public String testAOP(@RequestParam("userType") String userType){
        return null;
    }

    public String testLogin(){
        return null;
    }


}
