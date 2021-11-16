package nyu.alex.utils.dataUtils;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data

public class TrackSpendingUtils {

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    private String email;
    private String status;
}
