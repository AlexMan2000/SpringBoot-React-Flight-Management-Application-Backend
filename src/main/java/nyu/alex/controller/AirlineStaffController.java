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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin("http://localhost:3000/*")
@RequestMapping("/airlineStaff")
public class AirlineStaffController {

    @Autowired
    private AirlineStaffService airlineStaffService;


//    @GetMapping("")
//    public String getPage(){
//        return "airlineStaff";
//    }

    @PostMapping("/addNewFlight")
    @ResponseBody
    public String addNewFlight(Flight flight){
        airlineStaffService.addNewFlight(flight);
        return "success";
    }

    @PostMapping("/updateStatus")
    @ResponseBody
    public String updateFlight(Flight flight){
        airlineStaffService.updateFlight(flight);
        return "success";
    }

    @PostMapping("/updateManyStatus")
    @ResponseBody
    public String updateFlights(Flight flight){
        airlineStaffService.updateFlights(flight);
        return "success";
    }

    @PostMapping("/addNewAirport")
    @ResponseBody
    public String addNewAirport(Airport airport){
        airlineStaffService.insertNewAirport(airport);
        return "success";
    }

    @PostMapping("/addNewAirplane")
    @ResponseBody
    public String addNewAirplane(Airplane airplane){
        airlineStaffService.insertNewAirplane(airplane);
        return "success";
    }





    @GetMapping("/showInsertPage")
    public String showAirplanePage(){
        return "insertAirplane";
    }

    /**
     * 表单提交校验
     */
    @PostMapping("/validateAirplane")
    public void validateAirplaneForm(HttpServletRequest request){

    }

    @PostMapping("/validateFlight")
    public void validateFlightForm(HttpServletRequest request){

    }


}
