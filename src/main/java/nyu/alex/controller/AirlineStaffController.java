package nyu.alex.controller;

import com.alibaba.fastjson.JSON;
import nyu.alex.aop.NeedOperator;
import nyu.alex.aop.NeedStaff;
import nyu.alex.dao.entity.*;
import nyu.alex.aop.NeedAdmin;
import nyu.alex.aop.NeedLogin;
import nyu.alex.service.AirlineStaffService;
import nyu.alex.service.CustomerService;
import nyu.alex.utils.serviceUtils.GrantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:3000/*")
@RequestMapping("/airlineStaff")
public class AirlineStaffController {

    @Autowired
    private AirlineStaffService airlineStaffService;

    @Autowired
    private CustomerService customerService;
    /**
     * Find all upcoming flights, regardless of which airlines or which customers
     * Used for requests from FlightCRUD.js when the filters are all none. (default view)
     * @return List of flights
     */
    @NeedStaff
    @GetMapping("/findAllFlights")
    public String findAllFlights(){
        List<Flight> allFlights = airlineStaffService.findAllFlights();
        Map<String,Object> results = new HashMap<>();
        results.put("records",allFlights);
        results.put("total",allFlights.size());
        results.put("success",true);

        return JSON.toJSONString(results);
    }


    /**
     * Find all the flights information within a particular airline.
     * Used for requests from View My Flight.js
     * @param airlineName
     * @return
     */
    @NeedStaff
    @GetMapping("/findAllFlightsForAirline")
    public String findAllFlightsForAirline(@RequestParam("airlineName") String airlineName){
        List<Flight> allFlights = airlineStaffService.findAllFlightsForAirline(airlineName);
        Map<String,Object> results = new HashMap<>();
        results.put("records",allFlights);
        results.put("total",allFlights.size());
        results.put("success",true);
        return  JSON.toJSONString(results);
    }

    /**
     * Find all the flights with filters (if any)
     * Used for requests from FlightCRUD.js
     * @param flight
     * @return
     */
    @NeedStaff
    @PostMapping("/findAllFilteredFlights")
    public String findAllFilteredFlights(@RequestBody Flight flight){
        List<Flight> allFlights = airlineStaffService.findAllFilteredFlights(flight);
        Map<String,Object> results = new HashMap<>();
        results.put("records",allFlights);
        results.put("total",allFlights.size());
        results.put("success",true);

        return JSON.toJSONString(results);
    }

    /**
     * Find view reports for a given airlineStaff
     * Used for requests from ViewReports.js
     * @param airlineName
     * @param startDate Specify the startDate for range searching
     * @param endDate Specify the endDate for range searching
     * @return
     */
    @NeedStaff
    @GetMapping("/viewReports")
    public String findViewReports(@RequestParam("airlineName") String airlineName,
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


        return JSON.toJSONString(airlineStaffService.findViewReport(airlineName,startDateObject,endDateObject));
    }

    /**
     * Find top destinations
     * Used for requests from TopDestinations.js
     * @param past
     * @return
     */
    @NeedStaff
    @GetMapping("/topDestinations")
    public String findTopDestinations(@RequestParam("past") String past){
        return JSON.toJSONString(airlineStaffService.findTopDestination(past));
    }

    /**
     * Revenue Comparison Function
     * Used for requests from RevenueComparison.js
     * @param past
     * @param airlineName
     * @return
     */
    @GetMapping("/revenueComparison")
    public String findRevenueInfo(@RequestParam("past") String past,@RequestParam("airlineName") String airlineName){
        return JSON.toJSONString(airlineStaffService.findRevenueInfo(past,airlineName));
    }

    /**
     * Find top K bookingAgent based on sales or commission with date range
     * Used for requests from ViewAgent.js
     * @param type
     * @param past
     * @param K
     * @return
     */
    @NeedStaff
    @GetMapping("/getTopK")
    public String findTopK(@RequestParam("type") String type,
                                  @RequestParam(value = "past",required = false) String past,
                                  @RequestParam("K") Integer K){
        if(type.equals("sales")){
            if(past.equals("month")){
            return JSON.toJSONString(airlineStaffService.findTopKSales(1,K));
            }else{
                return JSON.toJSONString(airlineStaffService.findTopKSales(12,K));
            }
        }else{
            return JSON.toJSONString(airlineStaffService.findTopKCommission(K));
        }
    }

    /**
     * View Frequent Customers with threshold
     * Used for requests from ViewFrequent.js
     * @param K
     * @param airlineName
     * @return
     */
    @NeedStaff
    @GetMapping("/getTopKCustomers")
    public String getTopKCustomersWithFlights(@RequestParam("K") String K,
                                                      @RequestParam(value="airlineName",required=false) String airlineName){

        return JSON.toJSONString(airlineStaffService.findTopKFreqCustomers(K,airlineName));
    }

    @NeedStaff
    @PostMapping("/findCustomersFlightAirline")
    public String findCustomerFlightsOnAirline(@RequestParam("email") String email,@RequestParam("airlineName") String airlineName){
        List<Flight> result = customerService.findCustomerFlightsOnAirline(email,airlineName);
        return JSON.toJSONString(result);
    }

    /**
     * Add new flight
     * Used for requests from FlightCRUD.js
     * @param flight
     * @return
     */
    @NeedAdmin
    @PostMapping("/addNewFlight")
    public String addNewFlight(@RequestBody Flight flight){
        Flight flightConflict = airlineStaffService.findConflictFlight(flight);
        Flight flightRes = airlineStaffService.findFlight(flight);
        if(flightRes!=null){
            return "Error";
        }
        if(flightConflict!=null){
            return "Occupied";
        }
        airlineStaffService.addNewFlight(flight);
        return "success";
    }

    /**
     * Update the status of the flight.
     * Used for requests from FlightCRUD.js
     * @param flight
     * @return
     */
    @NeedOperator
    @PostMapping("/updateStatus")
    public String updateFlight(@RequestBody Flight flight){
        airlineStaffService.updateFlight(flight);
        return "success";
    }

    // Not implemented
    @PostMapping("/updateManyStatus")
    public String updateFlights(@RequestBody Flight flight){
        airlineStaffService.updateFlights(flight);
        return "success";
    }

    /**
     * Add new airport, with authentication
     * Used for requests from EditAirport.js
     * @param airport
     * @return
     */
    @NeedAdmin
    @PostMapping("/addNewAirport")
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
     * Used for requests from EditAirplane.js
     * @param airplane
     * @return
     */
    @PostMapping("/addNewAirplane")
    @NeedAdmin
    public String addNewAirplane(@RequestBody Airplane airplane){
        Airplane airplaneRes = airlineStaffService.findAirplane(airplane);
        if(airplaneRes!=null){
            return "Error";
        }
        airlineStaffService.insertNewAirplane(airplane);
        return "success";
    }


    /**
     * Used for requests from EditAirplane.js, displaying the airplanes for a particular airline
     * @param airlineName
     * @return
     */
    @GetMapping("/getAllAirplanes")
    @NeedStaff
    public String showAllAirplanesForAirline(@RequestParam("airlineName") String airlineName){
        return JSON.toJSONString(airlineStaffService.findAllAirplanesForAirline(airlineName));
    }

    /**
     * Check if the airplane model exists.
     * @param airplane
     * @return
     */
    @PostMapping("/validateNewAirplane")
    @NeedStaff
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
    @NeedAdmin
    public String validateBookingAgent(@RequestParam("email") String email,
                                                   @RequestParam("airlineName") String airlineName){
        return JSON.toJSONString(airlineStaffService.validateAgent(email, airlineName));

    }

    /**
     * See if the permission level or levels to be granted has already existed in the database.
     * @param grantUtils
     * @return
     */
    @PostMapping("/validatePermission")
    @NeedAdmin
    public String validatePermission(@RequestBody GrantUtils grantUtils){

        return JSON.toJSONString(airlineStaffService.validateGrantPermission(grantUtils));
    }

    /**
     * Add booking agent for a given airline. Insert into the booking_agent_work_for table.
     * @param email
     * @param airlineName
     * @return
     */
    @PostMapping("/addBookingAgent")
    @NeedAdmin
    public String addBookingAgent(@RequestParam("email") String email,
                                  @RequestParam("airlineName") String airlineName){
        Map<String, Boolean> stringBooleanMap = JSON.parseObject(validateBookingAgent(email, airlineName),Map.class);
        if(stringBooleanMap.get("emailValid")==true&&stringBooleanMap.get("workingValid")==true){
            airlineStaffService.addAgent(email,airlineName);
            stringBooleanMap.put("success",true); }else{
        stringBooleanMap.put("success",false);}
        return JSON.toJSONString(stringBooleanMap);
    }

    /**
     * First validate if existed, then perform the insertion.
     * @param grantUtils
     * @return
     */
    @PostMapping("/grantPermission")
    @NeedAdmin
    public String grantPermission(@RequestBody GrantUtils grantUtils){
        Map<String, Boolean> stringBooleanMap = JSON.parseObject(validatePermission(grantUtils),Map.class);
        System.out.println(stringBooleanMap);
        if(stringBooleanMap.get("nameValid")==true&&stringBooleanMap.get("permissionValid")==true){
            airlineStaffService.grantAirlineStaff(grantUtils);
            stringBooleanMap.put("success",true); }
        else{stringBooleanMap.put("success",false);}
        return JSON.toJSONString(stringBooleanMap);
    }

    /**
     * Delete the flight.
     *  Used for requests from FlightCRUD.js
     * @param flightNum
     * @param airlineName
     * @return
     */
    @PostMapping("/deleteFlight")
    @NeedOperator
    public String deleteFlight(@RequestParam("flightNum") String flightNum,@RequestParam("airlineName") String airlineName){
        airlineStaffService.deleteFlight(flightNum,airlineName);
        return "success";
    }



}
