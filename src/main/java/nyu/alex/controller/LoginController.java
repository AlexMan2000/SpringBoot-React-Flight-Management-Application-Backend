package nyu.alex.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import nyu.alex.service.LoginService;
import nyu.alex.utils.serviceUtils.LoginUtils;
import nyu.alex.utils.serviceUtils.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@CrossOrigin("http://localhost:3000/*")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginUtils loginUtils;

    @Autowired
    private LoginService loginService;


    @PostMapping("")
    @ResponseBody
    public StatusMessage validate(HttpServletRequest request,@RequestBody LoginUtils loginUtils){

        String userType = loginUtils.getUserType();

        //将用户的登录信息保存到session中
        StatusMessage statusMessage = loginService.login(loginUtils);

        //用户信息认证
        boolean status = statusMessage.getStatus();
        if(status){
            //登录成功将用户信息存入Session对象中
            System.out.println("登录成功");
            request.getSession().setAttribute("userInfo",loginUtils);
            //显示对应的页面
//            return status;
        }
//        else{
//            //登录失败展示失败原因
//            Integer code = statusMessage.getStatusCode();
//            String s = statusMessage.getStatusMapping().get(code);
//            return s;
//        }
        return statusMessage;
    }


}
