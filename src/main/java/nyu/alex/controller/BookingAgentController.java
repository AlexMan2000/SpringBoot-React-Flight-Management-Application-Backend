package nyu.alex.controller;

import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Customer;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.entity.Ticket;
import nyu.alex.service.BookingAgentService;
import nyu.alex.utils.serviceUtils.PurchaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/bookingAgent")
public class BookingAgentController {

    @Autowired
    private BookingAgentService bookingAgentService;

    @PostMapping("/getMyFlights")
    @ResponseBody
    public List<Flight> getMyFlights(@RequestParam("email") String email,
                                     @RequestParam(value="default",required=false) Boolean defaultValue,
                                     @RequestParam(value="sourceAirport",required=false) String sourceAirport,
                                     @RequestParam(value="destAirport",required=false) String destAirport,
                                     @RequestParam(value="startDate",required=false) String startDate,
                                     @RequestParam(value="endDate",required=false) String endDate){
        System.out.println("haha");
        List<Flight> myFlights = bookingAgentService.getMyFlights(email, defaultValue,sourceAirport, destAirport, startDate, endDate);
        return myFlights;
    }

    @PostMapping("/purchaseTicket")
    @ResponseBody
    public Map<String,Object> purchaseTicket(@RequestBody PurchaseUtils purchaseForm){
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
        return returnValues;
    }

    @PostMapping("/findAllAvailableTickets")
    @ResponseBody
    public List<Ticket> findAllAvailableTickets(@RequestParam("airlineName") String airlineName, @RequestParam("flightNum") String flightNum){
        List<Ticket> allTickets = bookingAgentService.findAllTickets(airlineName, flightNum);
        return allTickets;
    }

    @PostMapping("/getAllAvailableFlights")
    @ResponseBody
    public List<Flight> getAllAvailableFlights(@RequestBody Flight flight){
        List<Flight> allAvailableFlights = bookingAgentService.findAllAvailableFlights(flight);
        return allAvailableFlights;
    }

    @GetMapping("/validateCustomer")
    @ResponseBody
    public Map<String, Boolean> findCustomerByEmail(@RequestParam("email")String email){
        return bookingAgentService.validateCustomer(email);
    }


    @GetMapping("/getCommissionInfo")
    @ResponseBody
    public BookingAgent getCommissionInfo(@RequestParam("email") String email,
                                    @RequestParam("startDate") String startDate,
                                    @RequestParam("endDate") String endDate) throws ParseException {
        Date startDateObject = Date.from(Instant.parse(startDate));
        Date endDateObject = Date.from(Instant.parse(endDate));
        BookingAgent bookingAgentInfo = bookingAgentService.findBookingAgentInfo(email, startDateObject, endDateObject);

        return bookingAgentInfo;
    }

    @GetMapping("/getTopKCommission")
    @ResponseBody
    public List<Customer> findTopKCommission(@RequestParam("K") Integer K){
        return bookingAgentService.findTopKCommssion(K);
    }

    @GetMapping("/getTopKTickets")
    @ResponseBody
    public List<Customer> findTopKTickets(@RequestParam("K") Integer K){
        return bookingAgentService.findTopKTickets(K);
    }
}
