package nyu.alex.dao.mapper;

import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.entity.Ticket;
import nyu.alex.utils.serviceUtils.PurchaseUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBookingAgentDao {

    //用于注册校验
    BookingAgent findBookingAgentByEmail(String email);

    BookingAgent findCommissionInfo(@Param("email") String email,@Param("interval") Integer interval);

    // 根据airline_name和flight_num查找所有的Ticket信息
    List<Ticket> findAllTicketInfo(@Param("airlineName") String airlineName, @Param("flightNum") String flightNum);

    List<Flight> findAllAvailableFlights();

    List<Flight> findMyFlights(Flight flight);

    void bookTicket(PurchaseUtils purchaseForm);

    void insertBookingAgent(BookingAgent bookingAgent);

    void updateBookingAgent(BookingAgent bookingAgent);
}
