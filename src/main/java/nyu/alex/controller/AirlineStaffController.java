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

    // For the airline company te user works for
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

    @GetMapping("/revenueComparison")
    @ResponseBody
    public List<DataRow> findRevenueInfo(@RequestParam("past") String past,@RequestParam("airlineName") String airlineName){
        return airlineStaffService.findRevenueInfo(past,airlineName);
    }

    /**
     * Find top K bookingAgent based on sales or commission.
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
     * View Frequent Customers
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

    @PostMapping("/updateManyStatus")
    @ResponseBody
    public String updateFlights(@RequestBody Flight flight){
        airlineStaffService.updateFlights(flight);
        return "success";
    }

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

    @PostMapping("/validateBookingAgent")
    @ResponseBody
    public Map<String,Boolean> validateBookingAgent(@RequestParam("email") String email,
                                                   @RequestParam("airlineName") String airlineName){
        return airlineStaffService.validateAgent(email, airlineName);
    }

    @PostMapping("/validatePermission")
    @ResponseBody
    public Map<String,Boolean> validatePermission(@RequestBody GrantUtils grantUtils){

        return airlineStaffService.validateGrantPermission(grantUtils);
    }

    @PostMapping("/addBookingAgent")
    @ResponseBody
    public Map<String,Boolean> addBookingAgent(@RequestParam("email") String email,
                                  @RequestParam("airlineName") String airlineName){
        Map<String, Boolean> stringBooleanMap = validateBookingAgent(email, airlineName);
        System.out.println(stringBooleanMap);
        if(stringBooleanMap.get("emailValid")==true&&stringBooleanMap.get("workingValid")==true){
            airlineStaffService.addAgent(email,airlineName);
            stringBooleanMap.put("success",true); }
        stringBooleanMap.put("success",false);
        return stringBooleanMap;
    }

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
