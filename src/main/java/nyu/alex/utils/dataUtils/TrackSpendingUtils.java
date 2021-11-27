package nyu.alex.utils.dataUtils;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data

public class TrackSpendingUtils {

    private Date startDate;
    private Date endDate;
    private String email;
    private String status;
}
