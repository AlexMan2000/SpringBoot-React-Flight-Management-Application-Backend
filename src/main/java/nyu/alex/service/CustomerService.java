package nyu.alex.service;

import nyu.alex.dao.entity.Customer;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.mapper.ICustomerDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.utils.dataUtils.DataRow;
import nyu.alex.utils.dataUtils.TicketInfo;
import nyu.alex.utils.dataUtils.TrackSpendingUtils;
import nyu.alex.utils.serviceUtils.PurchaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService {

    @Resource
    private ICustomerDao customerDao;

    @Autowired
    private IFlightDao flightDao;

    public List<Flight> findAllTakenFlights(String email,String airlineName){

        Customer customer = customerDao.findAllFlightsWithCustomer(email, airlineName);
        if(customer==null){
            System.out.println("No such customer");
            return null;
        }
        List<Flight> takenFlights = customer.getTakenFlights();
        return takenFlights;
    }

    public List<TicketInfo> findMyFilteredFlights(String email,
                                              String sourceAirport, String destAirport,
                                              Date startDate, Date endDate,String status){
        List<TicketInfo> flights = customerDao.findMyFilteredFlights(email,sourceAirport,destAirport,startDate,endDate,status);
        return flights;
    }

    public Map<String,Object> purchaseTicket(Map<String,Object> purchaseForm) {
        Map<String, Object> results = customerDao.bookTicket(purchaseForm);
        return results;
    }

    public List<DataRow> trackSpending(TrackSpendingUtils track){
        List<DataRow> dataRows = customerDao.trackSpending(track);
        return dataRows;
    }

    public List<Flight> findAllAvailableFlights(Flight flight){
        List<Flight> allAvailableFlights = flightDao.findAllAvailableFlights(flight);
        return allAvailableFlights;
    }

}
