package nyu.alex.service;

import nyu.alex.dao.entity.AirlineStaff;
import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Customer;
import nyu.alex.dao.mapper.IAirlineDao;
import nyu.alex.dao.mapper.IAirlineStaffDao;
import nyu.alex.dao.mapper.IBookingAgentDao;
import nyu.alex.dao.mapper.ICustomerDao;
import nyu.alex.utils.serviceUtils.RegisterUtils;
import nyu.alex.utils.serviceUtils.StatusMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterService {

    @Resource
    private RegisterUtils registerUtils;

    @Resource
    private IAirlineStaffDao airlineStaffDao;

    @Resource
    private IBookingAgentDao bookingAgentDao;

    @Resource
    private ICustomerDao customerDao;

    private StatusMessage status;

    private String userType;

    //根据用户类型调用不同注册函数
    public String encodePassword(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    //注册Customer
    public void registerCustomer(Customer customer) {
        //对password进行md5操作,在后端转化
        String password = customer.getPassword();
//        customer.setPassword(encodePassword(password));
        customerDao.insertCustomer(customer);
    }
    public void registerAirlineStaff(AirlineStaff airlineStaff) {
        String password = airlineStaff.getPassword();
//        airlineStaff.setPassword(encodePassword(password));
        airlineStaffDao.insertNewAirlineStaff(airlineStaff);
    }

    public void registerBookingAgent(BookingAgent bookingAgent) {
        String password = bookingAgent.getPassword();
//        bookingAgent.setPassword(encodePassword(password));
        bookingAgentDao.insertBookingAgent(bookingAgent);
    }

    public Map<String, Boolean> validateCustomer(String email) {
        Customer customerByEmail = customerDao.findCustomerByEmail(email);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("valid", true);
        if (customerByEmail != null) {
            map.put("valid", false);
        }
        return map;
    }

    public Map<String, Boolean> validateAirlineStaff(String username) {
        AirlineStaff airlineStaff = airlineStaffDao.findAirlineStaffByName(username);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("valid", true);
        if (airlineStaff != null) {
            map.put("valid", false);
        }
        return map;
    }

    public Map<String, Boolean> validateBookingAgent(String email) {
        BookingAgent bookingAgent = bookingAgentDao.findBookingAgentByEmail(email);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("valid", true);
        if (bookingAgent != null) {
            //注册成功
            map.put("valid", false);
        }
        return map;
    }
}
