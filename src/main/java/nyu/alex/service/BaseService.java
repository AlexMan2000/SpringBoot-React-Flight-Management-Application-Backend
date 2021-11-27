package nyu.alex.service;

import nyu.alex.dao.entity.Airport;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.mapper.IAirportDao;
import nyu.alex.dao.mapper.IFlightDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

/**
 * Create the service that all types of users can enjoy.
 */
@Service
public class BaseService {

    @Autowired
    private IFlightDao flightDao;

    @Autowired
    private IAirportDao airportDao;


    public List<Flight> searchFlights(Flight flight){
        List<Flight> results = flightDao.findAllFlightsWithFilters(flight);
        for(Flight flight1:results){
            System.out.println(flight1);
        }
        return results;

    }

    public List<Flight> findAllFlights(){
        List<Flight> results = flightDao.findAllFlights();
        return results;
    }

    public List<Airport> searchAirports(){
        return airportDao.findAllAirports();
    }


    public List<Flight> showStatus(){
        return null;
    }


}
