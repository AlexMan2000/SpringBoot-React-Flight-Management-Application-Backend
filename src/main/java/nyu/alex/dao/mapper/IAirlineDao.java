package nyu.alex.dao.mapper;

import nyu.alex.dao.entity.Airline;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAirlineDao {

    Airline findAirlineByName(@Param("name") String airlineName);

    List<Airline> findAllAirlines();
}
