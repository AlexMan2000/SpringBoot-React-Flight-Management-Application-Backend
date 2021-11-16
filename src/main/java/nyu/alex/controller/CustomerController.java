package nyu.alex.controller;

import nyu.alex.dao.entity.Flight;
import nyu.alex.dao.mapper.ICustomerDao;
import nyu.alex.dao.mapper.IFlightDao;
import nyu.alex.service.CustomerService;
import nyu.alex.utils.dataUtils.DataRow;
import nyu.alex.utils.dataUtils.TicketInfo;
import nyu.alex.utils.dataUtils.TrackSpendingUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin("http://localhost:3000/*")
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private IFlightDao flightDao;

    @Autowired
    private CustomerService customerService;

    @GetMapping("")
    public String getPage(){
        return "customer";
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder bin){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor cust = new CustomDateEditor(sdf,true);
        bin.registerCustomEditor(Date.class,cust);
    }

    @PostMapping("/getMyFlights")
    @ResponseBody
    public List<TicketInfo> getMyFilteredFlights(@RequestParam("email") String email,
                                                 @RequestParam(value = "sourceAirport",required = false) String sourceAirport,
                                                 @RequestParam(value = "destAirport",required = false) String destAirport,
                                                 @RequestParam(value = "startDate",required = false) String startDate,
                                                 @RequestParam(value = "endDate",required = false) String endDate,
                                                 @RequestParam(value = "default",required = false) String status) throws ParseException {
        Date parsedStartDate = null;
        Date parsedEndDate = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if(startDate!=null){
            parsedStartDate = df.parse(startDate);
        }
        if(endDate!=null){
            parsedEndDate = df.parse(endDate);
        }
        List<TicketInfo> flights = customerService.findMyFilteredFlights(email,sourceAirport,destAirport,parsedStartDate,parsedEndDate,status);
        return flights;
    }

    @GetMapping("/purchaseFlight")
    @ResponseBody
    public Map<String,String> purchaseTicket(){
        Map<String,String> resultMap = new HashMap<>();


        return resultMap;
    }


    @PostMapping("/trackSpending")
    @ResponseBody
    public List<DataRow> trackSpending(@RequestBody TrackSpendingUtils track){
//        Map<Date,Float> trackedSpending = new HashMap<>();
        List<DataRow> dataRow = customerService.trackSpending(track);
        return dataRow;

    }






}
