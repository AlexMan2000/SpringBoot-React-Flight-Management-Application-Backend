package nyu.alex.controller;

import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.entity.Ticket;
import nyu.alex.service.BookingAgentService;
import nyu.alex.utils.serviceUtils.PurchaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/bookingAgent")
public class BookingAgentController {

    @Autowired
    private BookingAgentService bookingAgentService;

    @GetMapping("")
    public String getPage(){
        return "bookingAgent";
    }

    @PostMapping("/getMyFlights")
    @ResponseBody
    public List<Flight> getMyFlights(){
        List<Flight> myFlights = bookingAgentService.getMyFlights();
        return myFlights;
    }

    @PostMapping("/purchaseTicket")
    @ResponseBody
    public String purchaseTicket(@RequestBody PurchaseUtils purchaseForm){
        bookingAgentService.purchaseTicket(purchaseForm);
        return "success";
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

}
