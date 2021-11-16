package nyu.alex.service;

import nyu.alex.dao.entity.AirlineStaff;
import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Customer;
import nyu.alex.dao.mapper.IAirlineStaffDao;
import nyu.alex.dao.mapper.IBookingAgentDao;
import nyu.alex.dao.mapper.ICustomerDao;
import nyu.alex.utils.serviceUtils.LoginUtils;
import nyu.alex.utils.serviceUtils.StatusMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class LoginService {

    @Resource
    private ICustomerDao customerDao;

    @Resource
    private IAirlineStaffDao airlineStaffDao;

    @Resource
    private IBookingAgentDao bookingAgentDao;

    @Resource
    private StatusMessage status;

    public String md5Password(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public StatusMessage login(LoginUtils loginInfo) {
        String userType = loginInfo.getUserType();
        String password = loginInfo.getPassword();
        //MD5加密对比
//        password = md5Password(password);
        System.out.println(userType);
        if (userType.equals("customer")) {
            String email = loginInfo.getEmail();
            Customer customer = customerDao.findCustomerByEmail(email);
            if (customer == null) {
                status.setStatusCode(1);
            } else {
                String truePassword = customer.getPassword();
                // 登录成功
                if (truePassword.equals(password)) {
                    status.setStatus(true);
                } else {
                    status.setStatusCode(2);
                    status.setStatus(false);
                }
            }
        }
        else if (userType.equals("agent")) {
            String email = loginInfo.getEmail();
            Integer bookingId = loginInfo.getAgentId();
            BookingAgent bookingAgent = bookingAgentDao.findBookingAgentByEmail(email);
            if (bookingAgent == null) {
                status.setStatusCode(1);
            } else {
                String truePassword = bookingAgent.getPassword();
                Integer trueBookingId = bookingAgent.getBookingAgentId();
                boolean passwordStatus = truePassword.equals(password);
                boolean bookingIdStatus = trueBookingId.equals(bookingId);
                //登录成功
                if (passwordStatus && bookingIdStatus) {
                    status.setStatus(true);
                } else {
                    status.setStatus(false);
                    if (passwordStatus == false) {
                        status.setStatusCode(2);
                    } else {
                        status.setStatusCode(3);
                    }
                }
            }
        }
        else if (userType.equals("staff")) {
            String username = loginInfo.getUsername();
            AirlineStaff airlineStaff = airlineStaffDao.findAirlineStaffByName(username);
            if (airlineStaff == null) {
                status.setStatusCode(1);
            } else {
                String truePassword = airlineStaff.getPassword();
                //登录成功
                if (truePassword.equals(password)) {
                    status.setStatus(true);
                } else {
                    status.setStatusCode(2);
                    status.setStatus(false);
                }
            }
        }
        //返回status让LoginController设置用户信息到Session中做缓存
        return status;
    }
}