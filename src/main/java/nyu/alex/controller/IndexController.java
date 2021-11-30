package nyu.alex.controller;

import nyu.alex.dao.entity.Airport;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.mapper.IAirplaneDao;
import nyu.alex.dao.entity.Airplane;
import nyu.alex.dao.mapper.IAirportDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/index")
@CrossOrigin("http://localhost:3000/*")
public class IndexController {

    @Resource
    private IAirplaneDao airplaneDao;

    @Resource
    private BaseService baseService;

    @Resource
    private IFlightDao flightDao;


    @PostMapping("/searchFlights")
    @ResponseBody
    public List<Flight> searchFlights(@RequestBody Flight flight) {
        System.out.println("有搜索航班的请求");
        System.out.println(flight);
//        System.out.println(date);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        Date format = df.parse(date);
        return baseService.searchFlights(flight);
    }

    @GetMapping("/findAllFlights")
    @ResponseBody
    public List<Flight> findAllFlights(){
        return baseService.findAllFlights();
    }

    @GetMapping("/searchAirports")
    @ResponseBody
    public List<String> searchAllAirport(){
        List<Airport> airports = baseService.searchAirports();
        List<String> results = new ArrayList<>();
        for(Airport airport:airports){
            results.add(airport.getAirportName());
        }
        return results;
    }
}
