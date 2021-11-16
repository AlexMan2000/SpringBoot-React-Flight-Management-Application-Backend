package nyu.alex.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties("handler")
public class AirlineStaff implements Serializable {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String airlineName;
    private List<String> permissionDescription;
}
