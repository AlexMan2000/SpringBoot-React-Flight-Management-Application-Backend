package nyu.alex.controller;

import com.alibaba.fastjson.JSON;
import nyu.alex.aop.NeedAgent;
import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Customer;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.entity.Ticket;
import nyu.alex.service.BookingAgentService;
import nyu.alex.utils.serviceUtils.PurchaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/bookingAgent")
public class BookingAgentController {

    @Autowired
    private BookingAgentService bookingAgentService;

    /**
     * Get all the orders created, with filters
     * Used for View My Order function
     * @param email
     * @param defaultValue
     * @param sourceAirport
     * @param destAirport
     * @param startDate
     * @param endDate
     * @return
     */
    @NeedAgent
    @PostMapping("/getMyFlights")
    public String getMyFlights(@RequestParam("email") String email,
                                     @RequestParam(value="default",required=false) Boolean defaultValue,
                                     @RequestParam(value="sourceAirport",required=false) String sourceAirport,
                                     @RequestParam(value="destAirport",required=false) String destAirport,
                                     @RequestParam(value="startDate",required=false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,
                                     @RequestParam(value="endDate",required=false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate){

        List<Flight> myFlights = bookingAgentService.getMyFlights(email, defaultValue,sourceAirport, destAirport, startDate, endDate);
        return JSON.toJSONString(myFlights);
    }

    /**
     * Purchase tickets on behalf of a particular customer.
     * @param purchaseForm
     * @return
     */
    @NeedAgent
    @PostMapping("/purchaseTicket")
    public String purchaseTicket(@RequestBody PurchaseUtils purchaseForm){
        // 由于后端需要调用储存过程，
        Map<String,Object> returnValues = new HashMap<>();
        returnValues.put("ticketNum","");
        returnValues.put("purchaseDate","");
        returnValues.put("commissionFee","");
        returnValues.put("flightNum",purchaseForm.getFlightNum());
        returnValues.put("airlineName",purchaseForm.getAirlineName());
        returnValues.put("email",purchaseForm.getEmail());
        returnValues.put("bookingAgentId",purchaseForm.getBookingAgentId());
        bookingAgentService.purchaseTicket(returnValues);
        return JSON.toJSONString(returnValues);
    }


    // Deprecated
    @NeedAgent
    @PostMapping("/findAllAvailableTickets")
    public String findAllAvailableTickets(@RequestParam("airlineName") String airlineName, @RequestParam("flightNum") String flightNum){
        List<Ticket> allTickets = bookingAgentService.findAllTickets(airlineName, flightNum);
        return JSON.toJSONString(allTickets);
    }

    /**
     * Get all the flights available to be purchased, namely all the upcoming flights
     * excluding those with cancelled or checking in status
     * @param flight
     * @return
     */
    @NeedAgent
    @PostMapping("/getAllAvailableFlights")
    public String getAllAvailableFlights(@RequestBody Flight flight){
        List<Flight> allAvailableFlights = bookingAgentService.findAllAvailableFlights(flight);
        return JSON.toJSONString(allAvailableFlights);
    }


    /**
     *
     * @param email
     * @return
     */
    @NeedAgent
    @GetMapping("/validateCustomer")
    public String findCustomerByEmail(@RequestParam("email")String email){
        return JSON.toJSONString(bookingAgentService.validateCustomer(email));
    }

    /**
     * Getting the commission information within a particular range of dates of a particular bookingAgent.
     * Used for requests from CommissionStatistics.js
     * @param email
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    @NeedAgent
    @GetMapping("/getCommissionInfo")
    public String getCommissionInfo(@RequestParam("email") String email,
                                    @RequestParam("startDate") String startDate,
                                    @RequestParam("endDate") String endDate) throws ParseException {
        Date startDateObject = Date.from(Instant.parse(startDate));
        Date endDateObject = Date.from(Instant.parse(endDate));
        BookingAgent bookingAgentInfo = bookingAgentService.findBookingAgentInfo(email, startDateObject, endDateObject);

        return JSON.toJSONString(bookingAgentInfo);
    }


    /**
     * Top 5 customers based on number of commission received in the last year.
     * @param K
     * @return
     */
    @NeedAgent
    @GetMapping("/getTopKCommission")
    public String findTopKCommission(@RequestParam("K") Integer K){
        return JSON.toJSONString(bookingAgentService.findTopKCommssion(K));
    }


    /**
     * Top K customers based on past 6 months and top 5 customers based on tickets bought from the booking agent in the past 6 months.
     * @param K
     * @return
     */
    @NeedAgent
    @GetMapping("/getTopKTickets")
    public String findTopKTickets(@RequestParam("K") Integer K){
        return JSON.toJSONString(bookingAgentService.findTopKTickets(K));
    }
}
