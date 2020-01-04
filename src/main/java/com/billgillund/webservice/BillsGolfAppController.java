package com.billgillund.webservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BillsGolfAppController {
  
	 @RequestMapping(value="/billsgolfapp", method= RequestMethod.GET)
     public String getLoginPage(){
               return "thymeleaf/billsgolfapp";
    }
	 
	 @RequestMapping(value="/maintenance", method= RequestMethod.GET)
     public String getMaintenance(){
               return "thymeleaf/maintenance";
    }
   
}
