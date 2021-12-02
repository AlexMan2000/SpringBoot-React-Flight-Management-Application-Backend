package nyu.alex.dao.mapper;


import nyu.alex.dao.entity.Flight;
import nyu.alex.utils.dataUtils.FlightInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

@Mapper
public interface IFlightDao {

    /**
     * Find the information for all the flights
     * @return
     */
    List<Flight> findAllFlights();

    List<Flight> findAllAvailableFlights(Flight flight);

    //带upcoming约束
    List<Flight> findAllFlightsWithFilters(Flight flight);

    //不带upcoming约束
    List<Flight> findAllFlightsFiltered(Flight flight);

    //Deprecated
    List<FlightInfo> findAllFlightStatus();

    List<Flight> findAllFlightsAirlineStaffDefault(@Param("airlineName") String airlineName);

    List<Flight> findAllFlightsForAirline(@Param("airlineName") String airline_name);

    Flight findFlightByNum(String flight_num);



}
