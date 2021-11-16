package nyu.alex.dao.mapper;

import nyu.alex.dao.entity.BookingAgent;
import org.apache.ibatis.annotations.Param;

public interface IBookingAgentDao {

    //用于注册校验
    BookingAgent findBookingAgentByEmail(String email);

    BookingAgent findCommissionInfo(@Param("email") String email,@Param("interval") Integer interval);

    void bookTicket();

    void insertBookingAgent(BookingAgent bookingAgent);

    void updateBookingAgent(BookingAgent bookingAgent);
}
