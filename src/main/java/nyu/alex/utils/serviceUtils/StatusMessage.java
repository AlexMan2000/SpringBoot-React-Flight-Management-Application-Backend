package nyu.alex.utils.serviceUtils;

import lombok.Data;
import nyu.alex.dao.entity.AirlineStaff;
import nyu.alex.dao.entity.BookingAgent;
import nyu.alex.dao.entity.Customer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
@Scope("prototype")
public class StatusMessage {

    private boolean status=false;

    private String userType;

    private Integer statusCode;

    private String statusMessage;

    private Map<Integer,String> statusMapping;

    private AirlineStaff airlineStaff;

    private Customer customer;

    private BookingAgent bookingAgent;

    public StatusMessage(){
        statusMapping = new HashMap<>();
        statusMapping.put(1,"User Not Found!");
        statusMapping.put(2,"Password Error!");
        statusMapping.put(3,"BookingId Error!");
        statusMapping.put(4,"Someone Else Already Registered!");
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
