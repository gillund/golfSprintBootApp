package com.billgillund.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {
	@Autowired
	HttpServletRequest request;
	 
	@RequestMapping(value="logout", method= RequestMethod.GET)
    public String logout(ModelMap model) {
		
		HttpSession session = request.getSession(true);
		String  savedPlayer = (String)session.getAttribute("sessionPlayer");
		System.out.println("returning Session ID =  " + session.getId() + " for Player " + savedPlayer + "b4 logout");

		session.invalidate(); //close the session.
		
		System.out.println("returning Session ID =  " + session.getId() + " for Player " + savedPlayer + "after logout");
		
		String message = savedPlayer + " HAS BEEN LOGOUT";
    	request.setAttribute("message", message);

		return "jsp/login";
 
	}
}
