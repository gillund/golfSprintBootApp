package com.billgillund.webservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.billgillund.business.domain.SelectedPlayer;
import com.billgillund.business.services.CourseService;
import com.billgillund.business.services.PlayerHandicapService;
import com.billgillund.entity.Courses;
import com.billgillund.entity.Players;
import com.billgillund.entity.Round;

@Controller
public class ScoreController {
	@Autowired 
 	HttpServletRequest request;
	@Autowired
    CourseService courseService;
	
	@Autowired
	PlayerHandicapService phs;
	@RequestMapping(value="/processScore", method= RequestMethod.GET)
     public String processScore(@RequestParam("date") String date,
    		 					@RequestParam("courses") String[] courses,
    		 					@RequestParam("score") String score,
    		 					ModelMap modelMap) {
		String message = "";
		Hashtable c 					= courseService.getAllCourses();
		List sortedCourses 					= getCoursesJava8(c);
		modelMap.addAttribute("Courses", sortedCourses);
		
		HttpSession session = request.getSession();;
    	Players p = (Players) session.getAttribute("sessionPlayerObject");
		
		message = validateRequest(message,date,score,courses,modelMap,p);
	
    	modelMap.addAttribute("Message", message);

    	SelectedPlayer  sp =null;
    	List ps =null;
   	 	
    		
    	sp = getPlayerHandicap(p);
    	ps = getPlayerScores(p);
	
    	request.setAttribute("Player", sp);
    	request.setAttribute("playerScores", ps);	
    
		
		return "jsp/selectedplayer";
	}
	
	
public String validateRequest(String message, String p_date,String p_score, String[] p_courses,ModelMap modelMap,Players p){
    	
    	String selPlayer = getSessionPlayer(request);
		String addDate =  p_date;
		String addCourse[] = p_courses;
		String addScore =  p_score;
		int newScore =0;
		try{
		   newScore = Integer.parseInt(addScore);
		}
		catch(Exception e){
		  message = "Invalid Score";
		}
		
	    if (validateJavaDate(addDate)  == false)
		{
				message = "Date Required";
		}
		if (addCourse == null || addCourse == null)
		{
			message = "Course needs to be Highlighted";
		}
		if (newScore == 0){
			message = "Score must be greater than Zero ";
		}
		
		// try to add 
		if ((message.equals(""))) 
		{
		   int comma = addCourse[0].indexOf(",");
		   String charCourseId = addCourse[0];  // first item is ID
		   String courseName = addCourse[1];   // Second item is course name
		
		   
		   
		   try {
			   int courseID= Integer.parseInt(charCourseId);
			   phs.addScore(addDate, getSessionPlayer(request), courseName, newScore,courseID,p.getPlayerId());
		   } catch (Exception e) {
			  message = "Score Not Added - Bad input or Database problem";
			   e.printStackTrace();
		   } 
		
		}
		  Round round = new Round();
		  round.setPlayer("");
		  round.setCourse("");
		  round.setRoundDate("");
		  modelMap.addAttribute("newRound", round);
		  
		if (message.equals("")){
		   message = getSessionPlayer(request) + " Shot " + addScore + " at " + addCourse[1] ;
		}
		
		return message;
    }
	

private String getSessionPlayer(HttpServletRequest request) {
	
	  String savedPlayer = null;
	  HttpSession session = request.getSession(true);
	  savedPlayer = (String)session.getAttribute("sessionPlayer");
	  System.out.println("returning Session ID =  " + session.getId() + " for Player " + savedPlayer);
	 return savedPlayer;
}
private void setSessionPlayer(HttpServletRequest request, String aplayer) {
	
	HttpSession session = request.getSession(true);
	session.setAttribute("sessionPlayer", aplayer);
	System.out.println("Saving Session ID =  " + session.getId() + " for Player " + aplayer);



	 }
public List getCoursesJava8(Hashtable courses)
{
	  
	 		List<Courses> sortedCourses = 
			 (List<Courses>) courses.values().stream()
			 .sorted(Comparator.comparing(Courses::getCourseName))
			 .collect(Collectors.toList());
	 
	 return sortedCourses;
	 
}
public boolean validateJavaDate(String strDate)
{
	 if (strDate == null)
	 {
		 return false;
	 }

	/* Check if date is 'null' */
	if (strDate.trim().equals(""))
	{
	    return false;
	}
	/* Date is not 'null' */
	else
	{
	    /*
	     * Set preferred date format,
	     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
	    SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
	    sdfrmt.setLenient(false);
	    /* Create Date object
	     * parse the string into date 
          */
	    try
	    {
	        Date javaDate = sdfrmt.parse(strDate); 
	      }
	    /* Date format is invalid */
	    catch (ParseException e)
	    {
	        return false;
	    }
	   
	    return true;
	}
}

public SelectedPlayer getPlayerHandicap(Players p){
	 SelectedPlayer sp = null;
	 
	 if (p !=null){
		 float handicap = phs.getHandicap(p);
		 float avg      = phs.getAverage(p);
		 float avgDelta = phs.getAverageDelta(p);
		 sp = new SelectedPlayer ( p.getName(), handicap,avg,avgDelta); 
	 }
	
	 return sp;
	
	 
 }
 
public List getPlayerScores(Players p ){
	
	 List playerScores =  phs.getScoresForPlayer(p.getPlayerId());
	
	 return playerScores;
 }
	

}
