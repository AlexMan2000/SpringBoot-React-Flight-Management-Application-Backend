package nyu.alex.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@JsonIgnoreProperties("handler")
public class Airplane implements Serializable {
    private String id;
    private String airline;
    private Integer seats;
}
