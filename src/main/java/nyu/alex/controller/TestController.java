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
import nyu.alex.utils.serviceUtils.LoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@CrossOrigin
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


    @GetMapping("/{email}")
    public String testFindCustomer(@PathVariable("email") String email, Model model){
        System.out.println(email);
        Customer customer = customerDao.findCustomerByEmail(email);
        model.addAttribute("customer",customer);
        return "test";
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

    @GetMapping("/commission")
    @ResponseBody
    public String testFindCommissionInfo(){
        BookingAgent bookingAgent = bookingAgentDao.findCommissionInfo("cathayEmp01@csair.com",100);
        System.out.println(bookingAgent);
        return "sucess";
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


    @GetMapping("/insertFlight")
    public String testInsertNewFlight(){
        Flight flight = new Flight();
        flight.setFlightNum("MU7275");
        flight.setAirlineName("Cathay Pacific");
        flight.setAirplaneId("A550");
        flight.setDepartureTime(new Date(50876543));
        flight.setArrivalTime(new Date(50876545));
        flight.setPrice(9804.52f);
        flight.setStatus("Upcoming");
        flight.setSourceAirportName("AUH");
        flight.setDestAirportName("SZX");
        airlineStaffDao.insertNewFlight(flight);
        return "success";
    }

    @GetMapping("/updateFlight")
    @ResponseBody
    public String testUpdateFlight(){
        Flight flight = new Flight();
        flight.setFlightNum("MU7253");
        flight.setAirlineName("Cathay Pacific");
        flight.setAirplaneId("A550");
        flight.setDepartureTime(new Date(10876543));
        flight.setArrivalTime(new Date(10876543));
        flight.setPrice(9980.52f);
        flight.setStatus("Delayed");
        flight.setSourceAirportName("AUH");
        flight.setDestAirportName("SZX");
        airlineStaffDao.updateFlight(flight);
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


//    public Map<String,List<?>> getData() throws ParseException {
//        DateFormat df=  new SimpleDateFormat("yyyy-MM-dd");
//        Date[] tmp = {new Date(df.parse("2021-08-01").getTime()),new Date(df.parse("2021-09-01").getTime())};
////        List<Map<String, Float>> dateValueMap = customerDao.trackSpending("abababababa@gmail.com", tmp);
//        List<DataRow> dataRows = customerDao.trackSpending("abababababa@gmail.com", tmp);
//        List<Integer> dates = new ArrayList<>();
//        List<Float> values = new ArrayList<>();
//        for(DataRow row:dataRows){
//            dates.add(row.getTimestamp());
//            values.add(row.getValue());
//        }
//        System.out.println(dates);
//        System.out.println(values);
//        Map<String,List<?>> result = new HashMap<>();
//        result.put("dataRow",dataRows);
//        result.put("dates",dates);
//        result.put("values",values);
//        return result;
//    }

//    @GetMapping("/chart")
//    public String testChart(Model model) throws ParseException {
//        Map<String, List<?>> data = getData();
//        model.addAttribute("data",data.get("dataRow"));
//        model.addAttribute("date",data.get("dates"));
//        model.addAttribute("values",data.get("values"));
//        return "customer";
//    }

    //ResponseBody在这里用于返回json格式的对象供前端接收
//    @GetMapping("/chartAjax")
//    @ResponseBody
//    public Map<String,List<?>> testAjaxPie(Model model) throws ParseException {
//        Map<String, List<?>> data = getData();
//        return data;
//    }

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

    @GetMapping("/testDashBoard")
    public String testDashBoard(){
        return "dashboard";
    }

    @GetMapping("/testAdd")
    public String testAdd(){
        return "emp/add";
    }

    @GetMapping("/testList")
    public String testList(){
        return "emp/list";
    }

    @GetMapping("/customer")
    public String customerPage(){
        return "customer";
    }

    @GetMapping("/airlineStaff")
    public String airlineStaffPage(){
        return "airlineStaff";
    }

    @GetMapping("/bookingAgent")
    public String bookingAgentPage(){
        return "bookingAgent";
    }

}
