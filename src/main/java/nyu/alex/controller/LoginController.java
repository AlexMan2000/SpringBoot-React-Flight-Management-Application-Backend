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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@CrossOrigin("http://localhost:3000/*")
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginUtils loginUtils;

    @Autowired
    private LoginService loginService;

    /**
     * Used for quick login in. (免登录认证)
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/token")
    @ResponseBody
    public StatusMessage tokenAuth(HttpServletRequest request,HttpServletResponse response) {
        System.out.println("执行tokenAuth方法");
        System.out.println("=====================================");
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 如果有登录记录,免登录跳转
                if (cookie.getName().equals("JSESSIONID")) {
                    String sessionId = cookie.getValue();
                    System.out.println(sessionId);
                    if (sessionId.equals(session.getId())) {
                        StatusMessage statusMessage = (StatusMessage) session.getAttribute("userInfo");
                        System.out.println(statusMessage);
                        return statusMessage;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 登录方法
     * @param request
     * @param response
     * @param loginUtils
     * @return
     */
    @PostMapping("")
    @ResponseBody
    public StatusMessage validate(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginUtils loginUtils){

        System.out.println("执行validate方法");
        System.out.println("+++++++++++++++++++++++++++");
        HttpSession session = request.getSession();


//        System.out.println(cookies);

        //将用户的登录信息保存到session中, StatusMessage中保存了用户的信息
        StatusMessage statusMessage = loginService.login(loginUtils);

        //用户信息认证
        boolean status = statusMessage.getStatus();
        statusMessage.setUserType(loginUtils.getUserType());
        if(status){
            //登录成功将用户信息存入Session对象中
            System.out.println("登录成功");


            session.setAttribute("userInfo",statusMessage);
            // 没有活动30分钟后,session失效
            session.setMaxInactiveInterval(30*60);

            // 设置Cookie用于免登录
            Cookie token = new Cookie("JSESSIONID", session.getId());
            token.setPath("/");
            response.addCookie(token);
        }
        return statusMessage;
    }


}
