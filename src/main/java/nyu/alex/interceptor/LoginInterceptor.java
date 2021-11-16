package nyu.alex.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginObject = request.getSession().getAttribute("loginInfo");
        if(loginObject==null){
            request.setAttribute("msg","You should login first");
            response.sendRedirect("/login");
            // Intercept the request for resources and return to the login page
            return false;
        }

        return true;
    }

}
