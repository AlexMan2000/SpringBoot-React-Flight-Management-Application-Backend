package nyu.alex.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@JsonIgnoreProperties("handler")
public class Flight implements Serializable {

    private String flightNum;
    private String airlineName;
    private String airplaneId;
    private Date departureTime;
    private Date arrivalTime;
    private Float price;
    private String status;
    private String sourceAirportName;
    private String destAirportName;
    private String date;

    private Airport sourceAirport;
    private Airport destAirport;
    private Airline airline;
    private Airplane airplane;
    private List<Customer> passengers;
    private Integer bookedSeatNumber;
}
