package com.billgillund.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.billgillund.business.services.PlayerService;
import com.billgillund.repository.PlayersRepository;

@Controller
public class RegisterPlayerController {
	@Autowired
    private PlayersRepository playerRepository;
	@Autowired
	private  PlayerService  playerService;
	@RequestMapping(value="/registerplayer", method= RequestMethod.GET)
	
    public String viewRegister(ModelMap model) {
		
		System.out.println("made it to viewRegister");
		
		String message  =  "Register a Player";
    	model.addAttribute("message",  message);
    	
    	
  		
        return "jsp/registerplayer";
	}
        
    @RequestMapping(value="/processregister", method= RequestMethod.POST)
    public String processRegister(@RequestParam("playerName") String playerName, 
    							  @RequestParam("email")String email, 
    				              @RequestParam("phone")String phone, 
    						      @RequestParam("phone")String userid, 
    						      @RequestParam("password")String password, ModelMap model)
    {
    	
    	String message = validatePlayers(playerName, email, phone, userid, password);
    
    	if (message == null)
    	{
    		addPlayers(playerName, email, phone, userid, password);
    	}
     
  	    if (message ==null)
  	    {
  	    	message = "Player Added";
  	    	playerName = ""; email=""; phone=""; userid="";password="";
  	    }
  	    
  	    model.addAttribute("message", message);
  	   
  	    
  	    return "jsp/registerplayer";
	  
    }
    
    
    
    
    public String validatePlayers (String playerName,String email,String phone,String userid,String password)
    {
    	String message = null;
    	if (playerName != null){
    	  	if (playerName.equals("")) {
    	  	    message = "Player name must exist";
    	  	 }
    	}
    	      
    	if (email != null){
    	    if (email.equals("")) {
    	   	     message = "email must exist";
    	   	}
    	 }
    	 if (userid != null){
    	    if (userid.equals("")) {
    	    	 message = "UserId requiredt";
    	    }
    	 }
    	 if (password != null){
    	  	if (password.equals("")) {
    	    	 message = "password must exist";
    	 	}
    	 }
    	 
    	
    	
    	return message;
    	
    }
    
    
    public String addPlayers (String playerName,String email,String phone,String userid,String password)
    {
    	String message=null;
    	 try {
    	       playerService.addPlayer(playerName, email, phone,userid,password);
    	    }
    	    catch(Exception e){
    	    	
    	    	message = e.getLocalizedMessage();
    	    	if (message =="")
    	    	{
    	    		message = "Player not Added - Possiple Duplicate";
    	    	}
    	    }  
    	  
    	return message;
    	
    }
    
    
    
    
}
