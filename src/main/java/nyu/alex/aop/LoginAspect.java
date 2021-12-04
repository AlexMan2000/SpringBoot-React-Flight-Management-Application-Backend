package nyu.alex.aop;

import com.alibaba.fastjson.JSON;
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
import java.util.Map;

@Aspect
@Component
public class LoginAspect {

    private static final Map<Integer,String> STATUSCODE =
            Map.of(501,"Need Admin",502,"Need Operator",503,"Need Login");

    @Around("@annotation(NeedLogin)")
    public String checkLogin(ProceedingJoinPoint jointPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session= request.getSession();
        System.out.println("检查登录");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 如果有登录记录,免登录跳转
                if (cookie.getName().equals("JSESSIONID")) {
                    String sessionId = cookie.getValue();
                    if (sessionId.equals(session.getId())) {
                        StatusMessage statusMessage = (StatusMessage) session.getAttribute("userInfo");
                        if(statusMessage!=null){
                        return (String)jointPoint.proceed();}
                    }
                }
            }
        }
        return JSON.toJSONString(Map.of("statusCode",503,"message",STATUSCODE.get(503)));
    }
}
