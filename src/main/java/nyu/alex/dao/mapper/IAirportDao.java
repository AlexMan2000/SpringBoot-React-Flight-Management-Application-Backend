package nyu.alex.dao.mapper;

import nyu.alex.dao.entity.Airport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAirportDao {

    List<Airport> findAllAirports();

    Airport findAirportByName(@Param("name") String airportName);

    void insertAirport(Airport airport);

}
