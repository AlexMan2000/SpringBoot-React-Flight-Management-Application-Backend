package nyu.alex.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties("handler")
public class BookingAgent implements Serializable {
    private String email;
    private String password;
    private Integer bookingAgentId;

    private Float commissionFees;
    private Float averageCommissionFees;
    private Integer ticketBooked;

    private List<String> airlineNames;
}
