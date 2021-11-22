package nyu.alex.service;

import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.entity.Ticket;
import nyu.alex.dao.mapper.IBookingAgentDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.utils.serviceUtils.PurchaseUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookingAgentService {

    @Resource
    private IFlightDao flightDao;

    @Resource
    private IBookingAgentDao bookingAgentDao;

    public void purchaseTicket(PurchaseUtils purchaseForm){
        bookingAgentDao.bookTicket(purchaseForm);
        // 有异常抛出返回false;
    }


    public List<Flight> getMyFlights(){

        return null;
    }


    public List<Ticket> findAllTickets(String airlineName, String flightNum){
        List<Ticket> allTicketInfo = bookingAgentDao.findAllTicketInfo(airlineName, flightNum);
        return allTicketInfo;
    }


    public BookingAgent findBookingAgentInfo(String email,Integer interval){
        BookingAgent commissionInfo = bookingAgentDao.findCommissionInfo(email, interval);
        System.out.println(commissionInfo);
        return commissionInfo;
    }


    public List<Flight> findAllAvailableFlights(Flight flight){
        List<Flight> allAvailableFlights = flightDao.findAllAvailableFlights(flight);
        return allAvailableFlights;
    }


}
