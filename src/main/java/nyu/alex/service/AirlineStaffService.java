package nyu.alex.service;

import nyu.alex.dao.entity.*;
import nyu.alex.dao.mapper.IAirlineStaffDao;
import nyu.alex.dao.mapper.ICustomerDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.utils.dataUtils.FlightInfo;
import nyu.alex.utils.dataUtils.TicketInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Query how many flights get delayed/on-time etc.
     * @return
     */
    public List<FlightInfo> findFlightsBasedOnStatus(){
        //Can be optimized with only one class member
        List<FlightInfo> flightStatus = flightDao.findAllFlightStatus();
        System.out.println(flightStatus);
        return flightStatus;
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

    public Airplane findAirplane(Airplane airplane){
       return airlineStaffDao.findAirplane(airplane);
    }

    public void insertNewFlight(Flight flight){
        airlineStaffDao.insertNewFlight(flight);
    }

    public List<Flight> findAllFlights(){
        return flightDao.findAllFlights();
    }


    public Flight findFlight(Flight flight) { return airlineStaffDao.findFlight(flight);}

    public void updateFlight(Flight flight ){
        airlineStaffDao.updateFlight(flight);
    }

    public void updateFlights(Flight flight ){
        airlineStaffDao.updateFlights(flight);
    }


    public void deleteFlight(Flight flight ){

    }

    public void deleteFlights(Flight flight ){


    }


}
