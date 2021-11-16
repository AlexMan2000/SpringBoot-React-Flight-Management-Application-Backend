package nyu.alex.dao.mapper;

import nyu.alex.dao.entity.Customer;
import nyu.alex.dao.entity.Flight;
import nyu.alex.utils.dataUtils.DataRow;
import nyu.alex.utils.dataUtils.TicketInfo;
import nyu.alex.utils.dataUtils.TrackSpendingUtils;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface ICustomerDao {

    //用于注册校验
    Customer findCustomerByEmail(String emailAddress);

    void bookTicket();

    Customer findAllFlightsWithCustomer(@Param("email") String email, @Param("airlineName") String airlineName);

    Customer findAllFlightsWithCustomerFiltered(@Param("email") String email, @Param("airlineName") String airlineName,
                                        @Param("sourceAirport") String sourceAirport,@Param("destAirport") String destAirport,
                                        @Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("default") String status);


    List<TicketInfo> findMyFilteredFlights(@Param("email") String email,
                                           @Param("sourceAirport") String sourceAirport, @Param("destAirport") String destAirport,
                                           @Param("startDate") Date startDate,@Param("endDate") Date endDate, @Param("status") String status);


    void insertCustomer(Customer customer);

    //后续完善用户信息用
    void updateCustomer(Customer customer);

//    List<Map<String,Float>> trackSpending(@Param("email") String email, @Param("timespan") Date[] timespan);

    List<DataRow> trackSpending(TrackSpendingUtils track);
}
