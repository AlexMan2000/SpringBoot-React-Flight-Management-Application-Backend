package nyu.alex.dao.mapper;

import nyu.alex.dao.entity.*;
import nyu.alex.utils.dataUtils.DataRow;
import nyu.alex.utils.dataUtils.TicketInfo;
import nyu.alex.utils.serviceUtils.GrantUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IAirlineStaffDao {

    //用于登录注册校验以及权限管理
    AirlineStaff findAirlineStaffByName(String userName);

    AirlineStaff findAirlineStaffByNameAndAirline(GrantUtils grantUtils);

    //Deprecated
    List<Customer> findAllCustomerPeriod(@Param("type") String timespanType, @Param("interval")Integer timespan);

    List<Customer> findCustomersOnFlight(@Param("airlineName") String airlineName,@Param("flightNum") String flightNum);

    //Deprecated
    List<BookingAgent> findTopKBookingAgentEachPeriod(@Param("type")String timespanType,@Param("K")Integer K,@Param("interval")Integer timespan);



    List<TicketInfo> findAmountOfTicketsEachPeriod(@Param("year")int[] year);

    void insertNewAirplane(Airplane airplane);

    Airplane findAirplane(Airplane airplane);

    List<Airplane> findAllAirplanesForAirline(@Param("airlineName") String airlineName);

    void insertNewAirport(Airport airport);

    Airport findAirport(Airport airport);

    void insertNewFlight(Flight flight);

    Flight findFlight(Flight flight);

    void insertNewFlights(Flight flight);

    void insertNewAirlineStaff(AirlineStaff airlineStaff);

    void updateFlight(Flight flight);

    void updateFlights(Flight flight);

    void deleteFlight(@Param("flightNumber") String flightNumber,@Param("airlineName") String airlineName);

    void deleteFlights(@Param("key")Map<String, String> mapping) ;

    void updateAirlineStaff(AirlineStaff airlineStaff);

    // Used for validate if this bookingaAgent has been added or not
    BookingAgent findAgentByEmail(@Param("email") String email);

    // Used for validate if this bookingAgent has been added to the booking_agent_work_for or not
    BookingAgent findAgentWorkingFor(@Param("email") String email,@Param("airlineName") String airlineName);

    void addAgent(@Param("email") String email,@Param("airlineName") String airlineName);

    void grantAirlineStaff(GrantUtils grantUtils);

    List<Customer> findFreqCustomers(@Param("K") String K,
                                     @Param("airlineName") String airlineName);

    // View Agent
    List<DataRow> findTopKSales(@Param("past") Integer past,@Param("K") Integer K);

    //View Agent
    List<DataRow> findTopKCommission(@Param("K") Integer K);

    // View Report
    List<DataRow> findViewReports(@Param("airlineName") String airlineName, @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate
                                  );

    // Revenue Comparison
    List<DataRow> findRevenueInfo(@Param("past") String past,@Param("airlineName") String airlineName);

    List<DataRow> viewTopDestinations(@Param("past") String past);

}
