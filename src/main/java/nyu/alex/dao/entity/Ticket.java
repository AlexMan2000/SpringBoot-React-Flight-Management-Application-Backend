package nyu.alex.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties("handler")
public class Ticket implements Serializable {
    private String ticket_id;
    private String airlineName;
    private String flightNum;
}
