package nyu.alex.utils.serviceUtils;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope("prototype")
@Data
public class LoginUtils implements Serializable {

    private String userType;
    private String email;
    private String username;
    private String password;
    private Integer agentId;
    // 显示错误信息和用户提示
    private String tip;

}
