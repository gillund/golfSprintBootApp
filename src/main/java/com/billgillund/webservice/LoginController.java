package com.billgillund.webservice;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.billgillund.business.domain.SelectedPlayer;
import com.billgillund.business.services.CourseService;
import com.billgillund.business.services.PlayerHandicapService;
import com.billgillund.business.services.PlayerService;
import com.billgillund.entity.Courses;
import com.billgillund.entity.Players;
import com.billgillund.entity.Round;

@Controller
public class LoginController {
	
	 	@Autowired
		PlayerService playerService;
	 	@Autowired 
	 	HttpServletRequest request;
	 	@Autowired
		PlayerHandicapService phs;
	 	@Autowired
		CourseService courseService; 
	 
	 	@RequestMapping(value="login", method= RequestMethod.GET)
	    public String viewLogin(ModelMap model) {
	 
	 		Players player = getSessionPlayer(request);  // already logged in
	 		if(player!=null)
	 		{
	 		
		    	 processVldLogin(model, player );
		    	 return "jsp/selectedplayer";
	 		}
	 		
	 		String message  =  "Login Page";
	    	model.addAttribute("message",  message);
	    	
	    	
	  		
	        return "jsp/login";
	    }
	 	/*
		@RequestMapping(value="login", method= RequestMethod.POST)
	    public String processLogin(ModelMap model) {
	 
			String userName = (String) model.get("username");
	 	
	    	model.addAttribute("message",  userName);
	  		
	        return "jsp/login";
	    }
	    */
		   @PostMapping("/processLogin")                     // it only support port method
		    public String processLogin(@RequestParam("username") String userid,
		                              @RequestParam("password") String password,
		                              ModelMap modelMap) {
			   
			    Players player = null;
			    String validUser = userid+password;
			  	SelectedPlayer sp  = null;
		    	String message;
		    	String url;
			    boolean result =  playerService.isValidUserId(validUser);
			    if (result )
			    {
			    	 player = playerService.getPlayerByUserId(validUser);
			    	 setSessionPlayer(request, player.getName(),player);
			    	 processVldLogin(modelMap, player );
			    	 url = "jsp/selectedplayer";
					
			    }
			    else
			    {
			    	message = "INVALID USER";
			    	modelMap.addAttribute("message", message);
			    	url = "jsp/login";
			    }
			   
			   
			    return url;
		   }
		   
		   
		   private void setSessionPlayer(HttpServletRequest request, String aplayer, Players players) {
			 	
			 	 HttpSession session = request.getSession(true);
			     session.setAttribute("sessionPlayer", aplayer);
			     session.setAttribute("sessionPlayerObject", players);
			     System.out.println("Saving Session ID =  " + session.getId() + " for Player " + aplayer);
			 }
		   private void processVldLogin(ModelMap modelMap,Players player )
			{
			  	SelectedPlayer sp  = getPlayerHandicap(player);
		    	List<Round>	 ps = getPlayerScores(player.getPlayerId());
		    	String message =null;
				  
				  if (sp != null)
				  {
					  request.setAttribute("Player", sp);
					  request.setAttribute("playerScores", ps);
					  Round round = new Round();
					  request.setAttribute("newRound", round);
					 
				
					  Hashtable courses 					= courseService.getAllCourses();
					  List sortedCourses 					= getCoursesJava8(courses);
					  modelMap.addAttribute("Courses", sortedCourses);
					
				 }
				  
			
			}	  
		    public SelectedPlayer getPlayerHandicap(Players player){
				 
			
				 SelectedPlayer sp = null;
				 
				 
				 if (player !=null){
					 float handicap = phs.getHandicap(player);
					 float avg      = phs.getAverage(player);
					 float avgDelta = phs.getAverageDelta(player);
					 sp = new SelectedPlayer ( player.getName(), handicap,avg,avgDelta); 
				 }
				
				 return sp;
				
				 
			 }
		    public List<Courses> getCoursesJava8(Hashtable courses)
		    {
		   	  
		   	 		List<Courses> sortedCourses = 
		   			 (List<Courses>) courses.values().stream()
		   			 .sorted(Comparator.comparing(Courses::getCourseName))
		   			 .collect(Collectors.toList());
		   	 
		   	 return sortedCourses;
		   	 
		    }
		    public List<Round> getPlayerScores(long playerId ){
		     	
		    	 List<Round> playerScores = phs.getScoresForPlayer(playerId);
		   	
		   	 return playerScores;
		    }
		    
		    private Players getSessionPlayer(HttpServletRequest request) {
				
		      Players p = null;
		  	  String savedPlayer = null;
		  	  HttpSession session = request.getSession(true);
		  	  savedPlayer = (String)session.getAttribute("sessionPlayer");
		  	  p =(Players)session.getAttribute("sessionPlayerObject");
		  	  System.out.println("returning Session ID =  " + session.getId() + " for Player " + savedPlayer);
		  	 return p;
		  }
		   
}
