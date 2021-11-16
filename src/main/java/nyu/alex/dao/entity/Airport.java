package nyu.alex.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties("handler")
public class Airport implements Serializable {
    private String airportName;
    private String airportCity;
}
