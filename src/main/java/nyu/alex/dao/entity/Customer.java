package nyu.alex.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties("handler")
public class Customer implements Serializable {
    private String email;
    private String name;
    private String password;
    private String buildingNumber;
    private String street;
    private String city;
    private String livingState;
    private String phoneNumber;
    private String passportNumber;
    private Date passportExpiration;
    private String passportCountry;
    private Date birthday;

    List<Flight> takenFlights;
}
