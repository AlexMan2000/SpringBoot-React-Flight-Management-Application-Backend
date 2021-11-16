package nyu.alex.utils.serviceUtils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class StatusMessage {

    private boolean status=false;

    private Integer statusCode;

    private String statusMessage;

    private Map<Integer,String> statusMapping;

    public StatusMessage(){
        statusMapping = new HashMap<>();
        statusMapping.put(1,"User Not Found!");
        statusMapping.put(2,"Password Error!");
        statusMapping.put(3,"BookingId Error!");
        statusMapping.put(4,"Someone Else Already Registered!");
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<Integer, String> getStatusMapping() {
        return statusMapping;
    }

    public void setStatusMapping(Map<Integer, String> statusMapping) {
        this.statusMapping = statusMapping;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
