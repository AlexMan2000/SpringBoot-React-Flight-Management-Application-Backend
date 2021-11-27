package nyu.alex.controller;

import nyu.alex.dao.entity.AirlineStaff;
import nyu.alex.dao.entity.Airplane;
import nyu.alex.dao.entity.Airport;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.mapper.IAirlineStaffDao;
import nyu.alex.dao.mapper.IAirplaneDao;
import nyu.alex.dao.mapper.IAirportDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.service.AirlineStaffService;
import nyu.alex.utils.dataUtils.DataRow;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/getTopK")
    @ResponseBody
    public List<DataRow> findTopK(@RequestParam("type") String type,
                                  @RequestParam(value = "past",required = false) String past,
                                  @RequestParam("K") String K){
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
     * 表单提交校验
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


    @PostMapping("/deleteFlight")
    @ResponseBody
    public String deleteFlight(@RequestParam("flightNum") String flightNum,@RequestParam("airlineName") String airlineName){
        airlineStaffService.deleteFlight(flightNum,airlineName);
        return "success";
    }

}
