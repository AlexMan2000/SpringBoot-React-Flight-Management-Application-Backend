package nyu.alex.service;

import nyu.alex.dao.entity.Airline;
import nyu.alex.dao.mapper.IAirlineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService {

    @Autowired
    private IAirlineDao airlineDao;

    public List<Airline> findAllAirlines(){
        return airlineDao.findAllAirlines();
    }
}
