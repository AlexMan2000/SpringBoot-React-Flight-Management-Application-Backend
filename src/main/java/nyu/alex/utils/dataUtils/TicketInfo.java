package nyu.alex.utils.dataUtils;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data
public class TicketInfo implements Serializable {
    private String ticketId;
    private String flightNumber;
    private String departAirportName;
    private String arrivalAirportName;
    private Timestamp departureTime;
    private Timestamp arrivalTime;
    private String status;
}
