package nyu.alex.service;

import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Customer;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.entity.Ticket;
import nyu.alex.dao.mapper.IBookingAgentDao;
import nyu.alex.dao.mapper.ICustomerDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.utils.serviceUtils.PurchaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookingAgentService {

    @Autowired
    private ICustomerDao customerDao;

    @Resource
    private IFlightDao flightDao;

    @Resource
    private IBookingAgentDao bookingAgentDao;

    public Map<String,Object> purchaseTicket(Map<String,Object> purchaseForm){
        Map<String,Object> result = bookingAgentDao.bookTicket(purchaseForm);
        // 有异常抛出返回false;

        return result;
    }


    public List<Flight> getMyFlights(String email,
                                     Boolean defaultValue,
                                     String sourceAirport,
                                     String destAirport,
                                     String startDate,
                                     String endDate){
         return bookingAgentDao.findMyFlights(email, defaultValue,sourceAirport, destAirport, startDate, endDate);
    }


    public List<Ticket> findAllTickets(String airlineName, String flightNum){
        List<Ticket> allTicketInfo = bookingAgentDao.findAllTicketInfo(airlineName, flightNum);
        return allTicketInfo;
    }


    public BookingAgent findBookingAgentInfo(String email, Date startDate,Date endDate){
        BookingAgent commissionInfo = bookingAgentDao.findCommissionInfo(email, startDate,endDate);
        System.out.println(commissionInfo);
        return commissionInfo;
    }


    public List<Flight> findAllAvailableFlights(Flight flight){
        List<Flight> allAvailableFlights = flightDao.findAllAvailableFlights(flight);
        return allAvailableFlights;
    }


    public Map<String, Boolean> validateCustomer(String email) {
        Customer customerByEmail = customerDao.findCustomerByEmail(email);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("valid", true);
        System.out.println(customerByEmail);
        if (customerByEmail == null) {
            map.put("valid", false);
        }
        return map;
    }

    public List<Customer> findTopKCommssion(Integer K){
        return bookingAgentDao.findTopKCommission(K);
    }

    public List<Customer> findTopKTickets(Integer K){
        return bookingAgentDao.findTopKTickets(K);
    }


}
