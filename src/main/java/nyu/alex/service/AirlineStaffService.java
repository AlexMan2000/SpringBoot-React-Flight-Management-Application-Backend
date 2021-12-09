package nyu.alex.service;

import nyu.alex.dao.entity.*;
import nyu.alex.dao.mapper.IAirlineStaffDao;
import nyu.alex.dao.mapper.ICustomerDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.utils.dataUtils.DataRow;
import nyu.alex.utils.dataUtils.FlightInfo;
import nyu.alex.utils.dataUtils.TicketInfo;
import nyu.alex.utils.serviceUtils.GrantUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AirlineStaffService {

    @Resource
    private IAirlineStaffDao airlineStaffDao;

    @Resource
    private IFlightDao flightDao;

    @Resource
    private ICustomerDao customerDao;




    /**
     * 1. Find all in-progress, upcoming, and previous flights
     * for the airline that they work for, as well as a list of
     * passengers for the flights.
     * @return
     */
    public List<Flight> findAllFlightsWithCustomer(){
        List<Flight> flights = flightDao.findAllFlightsForAirline("Cathay Pacific");
        System.out.println(flights);
        return flights;
    }

    /**
     * Find a list of all flights a particular Customer has
     * taken only on that particular airline.
     * @return
     */
    public List<Flight> findAllFlightsOfCustomer(String email,String airlineName){
        Customer customer = customerDao.findAllFlightsWithCustomer(email, airlineName);
        List<Flight> flightsWithCustomer = customer.getTakenFlights();
        return null;
    }


    // --------------------------------------------------------------------------------------------//


    public List<DataRow> findTopDestination(String past){
        return airlineStaffDao.viewTopDestinations(past);
    }


    public List<DataRow> findViewReport(String airlineName, Date startDate, Date endDate){
        return airlineStaffDao.findViewReports(airlineName,startDate,endDate);
    }

    public List<DataRow> findRevenueInfo(String past,String airlineName){

        return airlineStaffDao.findRevenueInfo(past,airlineName);
    }


    /**
     * Find Most frequent customers with their flight information.
     * @param K
     * @param airlineName
     * @return
     */
    public List<Customer> findTopKFreqCustomers(String K,
                                                String airlineName){
            return airlineStaffDao.findFreqCustomers(K,airlineName);

    }

    /**
     * Add new airplanes into the system
     * @param airplane
     */
    public void addNewAirplane(Airplane airplane){
        airlineStaffDao.insertNewAirplane(airplane);
    }

    /**
     * create new flights for the airline they work for
     * @param flight
     */
    public void addNewFlight(Flight flight){
        airlineStaffDao.insertNewFlight(flight);
    }

    /**
     * Set in progress flight statuses in
     * the system.
     */
    public void modifyStatus(Flight flight){
        airlineStaffDao.updateFlight(flight);
    }


    /**
     * See the most frequent customer within a given period
     * @param timespan
     * @param interval
     * @return
     */
    public Customer findAllCustomerPeriod(String timespan,Integer interval){
        List<Customer> customers = airlineStaffDao.findAllCustomerPeriod(timespan,interval);
        return null;
    }

    /**
     * See the amount of tickets sold in a given timespan
     * @param timespan
     * @return
     */
    public List<TicketInfo> findAmountOfTicketsEachPeriod(int[] timespan){

        return airlineStaffDao.findAmountOfTicketsEachPeriod(timespan);
    }

    //已测试

    /**
     * See the top five Booking Agents for a given period
     * @param timespan
     * @param K
     * @param interval
     * @return
     */
    public BookingAgent findTopKBookingAgentPastPeriod(String timespan,Integer K,Integer interval){
        List<BookingAgent> bookingAgents = airlineStaffDao.findTopKBookingAgentEachPeriod(timespan, K,interval);
        return null;
    }


    public void insertNewAirport(Airport airport){
        airlineStaffDao.insertNewAirport(airport);
    }

    public Airport findAirport(Airport airport){
        return  airlineStaffDao.findAirport(airport);
    }


    public void insertNewAirplane(Airplane airplane){

        airlineStaffDao.insertNewAirplane(airplane);
    }

    public void addAgent(String email,String airlineName){
        airlineStaffDao.addAgent(email,airlineName);
    }

    public Map<String,Boolean> validateAgent(String email,String airlineName){
        Map<String,Boolean> statusMapping = new HashMap<>();

        statusMapping.put("emailValid",true);
        statusMapping.put("workingValid",true);
        statusMapping.put("success",true);
        BookingAgent agentByEmail = airlineStaffDao.findAgentByEmail(email);
        BookingAgent agentWorkingFor = airlineStaffDao.findAgentWorkingFor(email, airlineName);
        if(agentByEmail == null){
            statusMapping.put("emailValid",false);
        }
        if(agentWorkingFor!=null){
            statusMapping.put("workingValid",false);
        }
        return statusMapping;
    }

    public Map<String,Boolean> validateGrantPermission(GrantUtils grantUtils){
        Map<String,Boolean> statusMapping = new HashMap<>();
        Set<String> permissionToBeGranted = new HashSet<>(grantUtils.getPermission());
        statusMapping.put("nameValid",true);
        statusMapping.put("permissionValid",true);
        statusMapping.put("success",true);
        AirlineStaff airlineStaffByName = airlineStaffDao.findAirlineStaffByNameAndAirline(grantUtils);

        if(airlineStaffByName==null){
            // 查无此人
            statusMapping.put("nameValid",false);
        }
        if(airlineStaffByName !=null){
            Set<String> permission_list =  new HashSet<>(airlineStaffByName.getPermissionDescription());
            if(permission_list.containsAll(permissionToBeGranted)) {
                statusMapping.put("permissionValid",false);
            }
        }
        return statusMapping;
    }

    public void grantAirlineStaff(GrantUtils grantUtils){

        airlineStaffDao.grantAirlineStaff(grantUtils);
    }

    public Airplane findAirplane(Airplane airplane){
       return airlineStaffDao.findAirplane(airplane);
    }

    public List<Airplane> findAllAirplanesForAirline(String airlineName){
        return airlineStaffDao.findAllAirplanesForAirline(airlineName);
    }

    public void insertNewFlight(Flight flight){
        airlineStaffDao.insertNewFlight(flight);
    }

    public List<Flight> findAllFlights(){
        return flightDao.findAllFlights();
    }

    public List<Flight> findAllFlightsForAirline(String airlineName){
        return flightDao.findAllFlightsForAirline(airlineName);
    }

    public List<Flight> findAllFilteredFlights(Flight flight){
        return flightDao.findAllFlightsFiltered(flight);
    }

    public Flight findConflictFlight(Flight flight){
        return airlineStaffDao.findConflictFlight(flight);
    }


    public Flight findFlight(Flight flight) { return airlineStaffDao.findFlight(flight);}

    public void updateFlight(Flight flight ){
        airlineStaffDao.updateFlight(flight);
    }

    public void updateFlights(Flight flight ){
        airlineStaffDao.updateFlights(flight);
    }


    public void deleteFlight(String flightNum,String airlineName){
        airlineStaffDao.deleteFlight(flightNum,airlineName);
    }

    public void deleteFlights(Flight flight ){


    }

    public List<DataRow> findTopKSales(Integer past,Integer K){
        return airlineStaffDao.findTopKSales(past,K);
    }

    public List<DataRow> findTopKCommission(Integer K){
        return airlineStaffDao.findTopKCommission(K);
    }

}
