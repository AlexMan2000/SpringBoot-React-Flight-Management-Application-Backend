package nyu.alex.dao.mapper;

import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Customer;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.entity.Ticket;
import nyu.alex.utils.serviceUtils.PurchaseUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IBookingAgentDao {

    //用于注册校验
    BookingAgent findBookingAgentByEmail(String email);

    BookingAgent findCommissionInfo(@Param("email") String email,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    List<Customer> findTopKTickets(@Param("K") Integer K);

    List<Customer> findTopKCommission(@Param("K") Integer K);

    // 根据airline_name和flight_num查找所有的Ticket信息
    List<Ticket> findAllTicketInfo(@Param("airlineName") String airlineName, @Param("flightNum") String flightNum);

    List<Flight> findAllAvailableFlights();

    List<Flight> findMyFlights(@Param("email") String email,
                               @Param("default") Boolean defaultValue,
                               @Param("sourceAirportName") String sourceAirport,
                               @Param("destAirportName") String destAirport,
                               @Param("startDate") Date startDate,
                               @Param("endDate") Date endDate);

    Map<String,Object> bookTicket(Map<String,Object> purchaseForm);

    void insertBookingAgent(BookingAgent bookingAgent);

    void updateBookingAgent(BookingAgent bookingAgent);
}
