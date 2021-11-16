package nyu.alex.utils.serviceUtils;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
@Scope("prototype")
public class RegisterUtils implements Serializable {

    private String userType;
    private String email;
    private String name;
    private String username;
    private String password;
    private String bookingID;
    // 显示错误信息和用户提示
    private String tip;
}
