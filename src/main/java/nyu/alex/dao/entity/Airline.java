package nyu.alex.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties("handler")
public class Airline implements Serializable {
    private String airlineName;

    private List<BookingAgent> bookingAgents;
}
