package nyu.alex.controller;

import nyu.alex.dao.entity.*;
import nyu.alex.dao.mapper.IAirlineStaffDao;
import nyu.alex.dao.mapper.IAirplaneDao;
import nyu.alex.dao.mapper.IAirportDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.service.AirlineStaffService;
import nyu.alex.utils.dataUtils.DataRow;
import nyu.alex.utils.serviceUtils.GrantUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("http://localhost:3000/*")
@RequestMapping("/airlineStaff")
public class AirlineStaffController {

    @Autowired
    private AirlineStaffService airlineStaffService;


    /**
     * Find all upcoming flights
     * @return
     */
    @GetMapping("/findAllFlights")
    @ResponseBody
    public Map<String,Object> findAllFlights(){
        List<Flight> allFlights = airlineStaffService.findAllFlights();
        Map<String,Object> results = new HashMap<>();
        results.put("records",allFlights);
        results.put("total",allFlights.size());
        results.put("success",true);

        return results;
    }

    @GetMapping("/findAllFlightsForAirline")
    @ResponseBody
    public Map<String,Object> findAllFlightsForAirline(@RequestParam("airlineName") String airlineName){
        List<Flight> allFlights = airlineStaffService.findAllFlightsForAirline(airlineName);
        System.out.println("方法执行");
        System.out.println("+=============================");
        Map<String,Object> results = new HashMap<>();
        results.put("records",allFlights);
        results.put("total",allFlights.size());
        results.put("success",true);

        return results;
    }

    /**
     * Find all the flights with filters
     * @param flight
     * @return
     */
    @PostMapping("/findAllFilteredFlights")
    @ResponseBody
    public Map<String,Object> findAllFilteredFlights(@RequestBody Flight flight){
        List<Flight> allFlights = airlineStaffService.findAllFilteredFlights(flight);
        Map<String,Object> results = new HashMap<>();
        results.put("records",allFlights);
        results.put("total",allFlights.size());
        results.put("success",true);

        return results;
    }

    /**
     * Find view reports for a given airlineStaff
     * @param airlineName
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/viewReports")
    @ResponseBody
    public List<DataRow> findViewReports(@RequestParam("airlineName") String airlineName,
                                         @RequestParam(value="startDate",required = false) String startDate,
                                         @RequestParam(value="endDate",required = false) String endDate){
        Date startDateObject = null;
        Date endDateObject = null;

        if(startDate!=null || endDate != null){
            startDateObject = Date.from(Instant.parse(startDate));
            endDateObject = Date.from(Instant.parse(endDate));
            System.out.println(startDateObject);
            System.out.println(endDateObject);
        }


        return airlineStaffService.findViewReport(airlineName,startDateObject,endDateObject);
    }

    /**
     * Find top destinations
     * @param past
     * @return
     */
    @GetMapping("/topDestinations")
    @ResponseBody
    public List<DataRow> findTopDestinations(@RequestParam("past") String past){
        return airlineStaffService.findTopDestination(past);
    }

    /**
     * Revenue Comparison Function
     * @param past
     * @param airlineName
     * @return
     */
    @GetMapping("/revenueComparison")
    @ResponseBody
    public List<DataRow> findRevenueInfo(@RequestParam("past") String past,@RequestParam("airlineName") String airlineName){
        return airlineStaffService.findRevenueInfo(past,airlineName);
    }

    /**
     * Find top K bookingAgent based on sales or commission with date range
     * @param type
     * @param past
     * @param K
     * @return
     */
    @GetMapping("/getTopK")
    @ResponseBody
    public List<DataRow> findTopK(@RequestParam("type") String type,
                                  @RequestParam(value = "past",required = false) String past,
                                  @RequestParam("K") Integer K){
        if(type.equals("sales")){
            if(past.equals("month")){
            return airlineStaffService.findTopKSales(1,K);
            }else{
                return airlineStaffService.findTopKSales(12,K);
            }
        }else{
            return airlineStaffService.findTopKCommission(K);
        }
    }

    /**
     * View Frequent Customers with threshold
     * @param K
     * @param airlineName
     * @return
     */
    @GetMapping("/getTopKCustomers")
    @ResponseBody
    public List<Customer> getTopKCustomersWithFlights(@RequestParam("K") String K,
                                                      @RequestParam(value="airlineName",required=false) String airlineName){

        return airlineStaffService.findTopKFreqCustomers(K,airlineName);
    }


    /**
     * Add new flight, with authentification
     * @param flight
     * @return
     */
    @PostMapping("/addNewFlight")
    @ResponseBody
    public String addNewFlight(@RequestBody Flight flight){
        Flight flightRes = airlineStaffService.findFlight(flight);
        if(flightRes!=null){
            return "Error";
        }
        airlineStaffService.addNewFlight(flight);
        return "success";
    }

    /**
     * Update the status of the flight.
     * @param flight
     * @return
     */
    @PostMapping("/updateStatus")
    @ResponseBody
    public String updateFlight(@RequestBody Flight flight){
        airlineStaffService.updateFlight(flight);
        return "success";
    }

    // Not implemented
    @PostMapping("/updateManyStatus")
    @ResponseBody
    public String updateFlights(@RequestBody Flight flight){
        airlineStaffService.updateFlights(flight);
        return "success";
    }

    /**
     * Add new airport, with authentication
     * @param airport
     * @return
     */
    @PostMapping("/addNewAirport")
    @ResponseBody
    public String addNewAirport(@RequestBody  Airport airport){
        Airport airportRes = airlineStaffService.findAirport(airport);
        System.out.println(airportRes);
        if(airportRes!=null){
            return "Error";
        }
        airlineStaffService.insertNewAirport(airport);
        return "success";
    }

    /**
     * Add new airplane, with authentication
     * @param airplane
     * @return
     */
    @PostMapping("/addNewAirplane")
    @ResponseBody
    public String addNewAirplane(@RequestBody Airplane airplane){
        Airplane airplaneRes = airlineStaffService.findAirplane(airplane);
        if(airplaneRes!=null){
            return "Error";
        }
        airlineStaffService.insertNewAirplane(airplane);
        return "success";
    }

    @GetMapping("/getAllAirplanes")
    @ResponseBody
    public List<Airplane> showAllAirplanesForAirline(@RequestParam("airlineName") String airlineName){
        return airlineStaffService.findAllAirplanesForAirline(airlineName);
    }

    /**
     * Check if the airplane model exists.
     * @param airplane
     * @return
     */
    @PostMapping("/validateNewAirplane")
    @ResponseBody
    public String validateNewAirplane(@RequestBody Airplane airplane){
        Airplane airplaneRes = airlineStaffService.findAirplane(airplane);
        if(airplaneRes==null){
            return "Error";
        }
        return "success";
    }

    /**
     * See if the bookingAgent to be added exists in the database in the first place
     * @param email
     * @param airlineName
     * @return
     */
    @PostMapping("/validateBookingAgent")
    @ResponseBody
    public Map<String,Boolean> validateBookingAgent(@RequestParam("email") String email,
                                                   @RequestParam("airlineName") String airlineName){
        return airlineStaffService.validateAgent(email, airlineName);
    }

    /**
     * See if the permission level or levels to be granted has already existed in the database.
     * @param grantUtils
     * @return
     */
    @PostMapping("/validatePermission")
    @ResponseBody
    public Map<String,Boolean> validatePermission(@RequestBody GrantUtils grantUtils){

        return airlineStaffService.validateGrantPermission(grantUtils);
    }

    /**
     * Add booking agent for a given airline. Insert into the booking_agent_work_for table.
     * @param email
     * @param airlineName
     * @return
     */
    @PostMapping("/addBookingAgent")
    @ResponseBody
    public Map<String,Boolean> addBookingAgent(@RequestParam("email") String email,
                                  @RequestParam("airlineName") String airlineName){
        Map<String, Boolean> stringBooleanMap = validateBookingAgent(email, airlineName);
        System.out.println(stringBooleanMap);
        if(stringBooleanMap.get("emailValid")==true&&stringBooleanMap.get("workingValid")==true){
            airlineStaffService.addAgent(email,airlineName);
            stringBooleanMap.put("success",true); }else{
        stringBooleanMap.put("success",false);}
        return stringBooleanMap;
    }

    /**
     * First validate if existed, then perform the insertion.
     * @param grantUtils
     * @return
     */
    @PostMapping("/grantPermission")
    @ResponseBody
    public Map<String,Boolean> grantPermission(@RequestBody GrantUtils grantUtils){
        Map<String, Boolean> stringBooleanMap = validatePermission(grantUtils);
        System.out.println(stringBooleanMap);
        if(stringBooleanMap.get("nameValid")==true&&stringBooleanMap.get("permissionValid")==true){
            airlineStaffService.grantAirlineStaff(grantUtils);
            stringBooleanMap.put("success",true); }
        else{stringBooleanMap.put("success",false);}
        return stringBooleanMap;
    }

    /**
     * Delete the flight.
     * @param flightNum
     * @param airlineName
     * @return
     */
    @PostMapping("/deleteFlight")
    @ResponseBody
    public String deleteFlight(@RequestParam("flightNum") String flightNum,@RequestParam("airlineName") String airlineName){
        airlineStaffService.deleteFlight(flightNum,airlineName);
        return "success";
    }



}
