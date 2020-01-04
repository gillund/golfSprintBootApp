<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD>
<link rel="stylesheet" href="css/stylev2.css"/>
</head>
<body>
</head>
<header id="home" class="header">
 <nav class="nav" role="navigation">
    <div class="container nav-elements">
      <div class="branding">
      </div><!-- branding -->
      <ul class="navbar">
       <h1>      <li><a href="maintenance">Handicap Maintenance</a></li> </h1> 
       <h1>      <li><a href="billsgolfapp">Home</a></li> </h1> 
        </ul><!-- navbar -->
    </div><!-- container nav-elements -->
  </nav>
  
  <h1> Add Score For Handicap </h1>
  
</header>


 <%@ page import="java.text.SimpleDateFormat, com.billgillund.business.services.* ,com.billgillund.business.domain.* , com.billgillund.entity.* , java.util.* " %>

<body>

<center>
<%
   SelectedPlayer selectedPlayer = (SelectedPlayer) request.getAttribute("Player");
  %>
   <table cellspacing="1" cellpadding="5">
   <tr>
     <td align="right">Player:  </td>
     <td > <font size=4 >  <%=selectedPlayer.getName() %> </font>  </td>
     <td align="right">Handicap:  </td>
     <td><font size=4 >  <%=selectedPlayer.getHandicap() %> </font> </td>
     <td align="right">Average Delta: </td>
     <td><font size=4 > <%= selectedPlayer.getDelta() %> </font></td>
     <td align="right">Average Score:  </td>
     <td><font size=4 > <%= selectedPlayer.getAvgScore() %> </font></td>
   </tr>
</table>

<center>
   <div >
  	 <table>
  	 <tr>
      		<th align="left"> Date </th>
      		<th align="left"> Course </th>
      		<th align="left"> Rating </th>
      		<th align="left"> Slope </th>
      		<th align="left"> Score </th>
      		<th align="left"> delta </th>
             <th align="left"> Used </th>
              <th align="left"> Round # </th>
     </tr>
     
   	 <tr align="left" >
 <%
   
 		List<Round> myList = (List) request.getAttribute("playerScores");
  
%>
<%
	   Iterator i = myList.iterator();
       int numberRound = 0;
	   while(i.hasNext()){
		  numberRound++;
   		  Round  r 			= (Round) i.next();
   		  String usedInCalc = "";
   		  if (r.isInUse()
   				  )
   		  {
   			  usedInCalc = "YES";
   		  }
   		  else
   		  { 
   			  usedInCalc = "";
	      }
   	 %>
  		  <td align="left"> <%= r.getRoundDate() %> 		</td >
      	  <td align="left"> <%= r.getCourse() %> 	</td>
      	  <td align="left"> <%= r.getRating() %> 	</td>
      	  <td align="left"> <%= r.getSlope() %> 	    </td> 
      	  <td align="left"> <%= r.getScore() %>  	</td > 
      	  <td align="left"> <%= r.getDelta() %> 	    </td> 
      	  <td align="left"> <%= usedInCalc %>  	</td > 
      	   <td align="left"> <%= numberRound %>  	</td >
      	  
   		</tr>
 	<%
     }
	%>
  </table>
</div>
</center>
<center>
<%
         // get parameters from the request
        Round r = (Round) request.getAttribute("newRound");
        
        String 	date 		    = r.getRoundDate();
        String 	course 			= r.getCourse();
        int 	score 			= r.getScore();
        
        String message = (String) request.getAttribute("Message");
        if (message == null){
        	message = "";
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        String todaysDate = sdf.format(cal.getTime());
  
        
        
  %>

<form action="/processScore" method="get" >
	<table cellspacing="1" cellpadding="5" border="0">
	<input type="hidden" name="function", value="ADDSCORE"> 
        <tr>
            <td align="right">Date:</td>
            
            <td><input type="date" name="date" size="5" style="cursor: text" /></td>
            <td align="right">Course:</td>
      
            <td> <select size =5 name="courses" multiple>
            <%
        	   List sortedCourses = (List) request.getAttribute("Courses");
        	   if (sortedCourses != null) {
        	      Iterator i2 = sortedCourses.iterator();
        	      while(i2.hasNext()){
        		     Courses c 			= (Courses) i2.next();
        		     String s = c.getCourseName() + "-" + 
        		     			c.getComment() + "," + " Slope= "+ 
        		     			c.getSlope() +  " Rating= " + 
        		     			c.getRating();
             %>
               <option  value="<%= c.getCourseId() + "," +c.getCourseName()   %>" > <%= s %>  </option>
               
            <%
        	      }
        	   }
            %>
            </td>
      
            <td align="right">Score:</td>
            <td><input type="text" name=score size="5"  value="<%= score %>"></td>
            <td> <input type="submit" value="Add Score" ></td>
        </tr>
    </table>
 </form>
</center>
<br>
<h1 > 
   <%= message %>
</h1>
</body>
</html>


      









