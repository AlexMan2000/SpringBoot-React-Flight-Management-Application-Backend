package nyu.alex.service;

import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.mapper.IBookingAgentDao;
import nyu.alex.dao.mapper.IFlightDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BookingAgentService {

    @Resource
    private IFlightDao flightDao;

    @Resource
    private IBookingAgentDao bookingAgentDao;

    public void purchaseTicket(){

    }


    public BookingAgent findBookingAgentInfo(String email,Integer interval){
        BookingAgent commissionInfo = bookingAgentDao.findCommissionInfo(email, interval);
        System.out.println(commissionInfo);
        return commissionInfo;
    }



}
