package nyu.alex.controller;

import nyu.alex.utils.serviceUtils.LoginUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin("http://localhost:3000/*")
@RequestMapping("/logout")
public class LogOutController {

    /**
     * Log user out
     * @param request
     * @return
     */
    @GetMapping("")
    public String logOutUser(HttpServletRequest request){
        HttpSession session = request.getSession();

        session.invalidate();

        return "success";
    }


}
