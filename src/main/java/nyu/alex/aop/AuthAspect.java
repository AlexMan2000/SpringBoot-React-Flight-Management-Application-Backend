package nyu.alex.aop;
import com.alibaba.fastjson.JSON;
import jdk.jshell.Snippet;
import nyu.alex.dao.entity.AirlineStaff;
import nyu.alex.utils.serviceUtils.StatusMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class AuthAspect {

    private static final Map<Integer,String> STATUSCODE =
            Map.of(501,"Need Admin",502,"Need Operator",503,"Need Login",504,"Role Error");

    public StatusMessage checkLogin(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session= request.getSession();
        System.out.println("检查登录");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 如果有登录记录,免登录跳转
                if (cookie.getName().equals("JSESSIONID")) {
                    String sessionId = cookie.getValue();
                    System.out.println(sessionId);
                    if (sessionId.equals(session.getId())) {
                        StatusMessage statusMessage = (StatusMessage) session.getAttribute("userInfo");
                        if(statusMessage!=null){
                        return statusMessage;}
                    }
                }
            }
        }
        return null;
    }

    @Around("@annotation(NeedCustomer)")
    public String checkCustomer(ProceedingJoinPoint jointPoint) throws Throwable{
        // 先判断是否登录
        StatusMessage statusMessage = checkLogin();
        if(statusMessage==null){
            return JSON.toJSONString(Map.of("statusCode",503,"message",STATUSCODE.get(503)));
        }
        if(!statusMessage.getUserType().equals("customer")){
            return JSON.toJSONString(Map.of("statusCode",504,"message",STATUSCODE.get(504)));
        }else{
            return (String) jointPoint.proceed();
        }

    }

    @Around("@annotation(NeedStaff)")
    public String checkStaff(ProceedingJoinPoint jointPoint) throws Throwable{
        // 先判断是否登录
        System.out.println("检查staff");
        StatusMessage statusMessage = checkLogin();
        if(statusMessage==null){
            return JSON.toJSONString(Map.of("statusCode",503,"message",STATUSCODE.get(503)));
        }
        if(!statusMessage.getUserType().equals("staff")){
            return JSON.toJSONString(Map.of("statusCode",504,"message",STATUSCODE.get(504)));
        }else{
            return (String) jointPoint.proceed();
        }

    }

    @Around("@annotation(NeedAgent)")
    public String checkUserAgent(ProceedingJoinPoint jointPoint) throws Throwable{
        // 先判断是否登录
        StatusMessage statusMessage = checkLogin();
        if(statusMessage==null){
            return JSON.toJSONString(Map.of("statusCode",503,"message",STATUSCODE.get(503)));
        }
        if(!statusMessage.getUserType().equals("agent")){
            return JSON.toJSONString(Map.of("statusCode",504,"message",STATUSCODE.get(504)));
        }else{
            return (String) jointPoint.proceed();
        }

    }

    @Around("@annotation(NeedAdmin)))")
    public String checkAdmin(ProceedingJoinPoint jointPoint) throws Throwable {
        StatusMessage statusMessage = checkLogin();
        System.out.println("检查Admin");
        if(statusMessage==null){
            // 用户未登录
            return JSON.toJSONString(Map.of("statusCode",503,"message",STATUSCODE.get(503)));
        }
        if(!statusMessage.getUserType().equals("staff")){
            return JSON.toJSONString(Map.of("statusCode",504,"message",STATUSCODE.get(504)));
        }
        AirlineStaff airlineStaff = statusMessage.getAirlineStaff();
        List<String> permissionList = airlineStaff.getPermissionDescription();
        if(permissionList.contains("Admin")){
            return (String)jointPoint.proceed();
        }else{
            return JSON.toJSONString(Map.of("statusCode",501,"message",STATUSCODE.get(501)));
        }
    }

    @Around("@annotation(NeedOperator)")
    public String checkOperator(ProceedingJoinPoint jointPoint) throws Throwable {
        StatusMessage statusMessage = checkLogin();
        System.out.println("检查Operator");
        if(statusMessage==null){
            // 用户未登录
            return JSON.toJSONString(Map.of("statusCode",503,"message",STATUSCODE.get(503)));
        }
        AirlineStaff airlineStaff = statusMessage.getAirlineStaff();
        List<String> permissionList = airlineStaff.getPermissionDescription();
        if(permissionList.contains("Operator")){
            return (String)jointPoint.proceed();
        }else{
            return JSON.toJSONString(Map.of("statusCode",502,"message",STATUSCODE.get(502)));
        }
    }
}
