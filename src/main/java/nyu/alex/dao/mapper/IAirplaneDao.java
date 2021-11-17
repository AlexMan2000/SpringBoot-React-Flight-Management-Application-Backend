package nyu.alex.dao.mapper;

import nyu.alex.dao.entity.Airplane;
import nyu.alex.dao.entity.Flight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IAirplaneDao {


    List<Airplane> findAllAirplanes();


    Airplane findAirplane(@Param("id") String id,@Param("airline") String airline);

    void insertAirplane(Airplane airplane);


}
