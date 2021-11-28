package nyu.alex.utils.dataUtils;

import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.Map;

//Not used for now
@Data
public class DataRow {

    private Date interval;
    private String email;
    private String type;
    private Integer timestamp;
    private Float value;

}
