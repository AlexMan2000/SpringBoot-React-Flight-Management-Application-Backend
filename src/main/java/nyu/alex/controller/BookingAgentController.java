package nyu.alex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/bookingAgent")
public class BookingAgentController {

    @GetMapping("")
    public String getPage(){
        return "bookingAgent";
    }

}
