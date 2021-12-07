package nyu.alex.dao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Data
@JsonIgnoreProperties("handler")
public class Flight implements Serializable {

    private String flightNum;
    private String airlineName;
    private String airplaneId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departureTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrivalTime;

//    private Timestamp departureTime;
//    private Timestamp arrivalTime;
    private Float price;
    private String status;
    private String sourceAirportName;
    private String destAirportName;

    private Airport sourceAirport;
    private Airport destAirport;
    private Airline airline;
    private Airplane airplane;
    private List<Customer> passengers;
    private Integer bookedSeatNumber;

    private boolean isFull;
    private Integer remainingSeats;
    private String customerEmail;
    private String ticketId;
    private Date purchaseDate;
}
