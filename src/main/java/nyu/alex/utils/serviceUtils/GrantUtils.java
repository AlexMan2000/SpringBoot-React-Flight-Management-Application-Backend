package nyu.alex.utils.serviceUtils;

import lombok.Data;

import java.util.List;

@Data
public class GrantUtils {
    private String userName;
    private String airlineName;
    private List<String> permission;
}
