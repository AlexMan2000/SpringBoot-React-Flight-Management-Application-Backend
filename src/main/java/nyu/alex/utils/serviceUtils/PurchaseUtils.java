package nyu.alex.utils.serviceUtils;

import lombok.Data;

import java.util.Date;

@Data
public class PurchaseUtils {

    private String flightNum;
    private String airlineName;
    private String ticketNum;
    private String email;
    private String bookingAgentId;
    private Date purchaseDate;
    private Float commissionFee;


}
