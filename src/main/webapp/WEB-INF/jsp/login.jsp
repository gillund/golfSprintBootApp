<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/style.css"/>
</head>
<header id="home" class="header">
 <nav class="nav" role="navigation">
    <div class="container nav-elements">
      <div class="branding">
      </div><!-- branding -->
      <ul class="navbar">
      <h1> <li><a href="/registerplayer">Register a Player</a></li> </h1> 
       <h1> <li><a href="/billsgolfapp">Home</a></li> </h1> 
        </ul><!-- navbar -->
    </div><!-- container nav-elements -->
  </nav>
</header>

  <br>
<body>

	<div class="centered" >
	   <div class="myDate">
		<!-- displays date and time for today --> 
		
		 <%
         // get parameters from the request
        String message = (String) request.getAttribute("message");
		if (message == null) message="";
		System.err.println("*****" + message + "*********");
  		%>
 
 		<h1><%=	displayDate() %></h1>
		</div>
	
		<div class="login">
			<form action="/processLogin" method="post" modelAttribute="loginAttributes" >
				<h2>Username</h2> 
				<input type="text" name="username" id="username">
				<br> 
				<h2>Password</h2> 
				<input type="password" name="password" id="password">
				<br> 
				<br>
				<input type="submit" name="function" value="Login" id="login">
			</form>
		</div>
		<br>
		
   </div>
<%!
	public String displayDate(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm");
		Date date = Calendar.getInstance().getTime();
		return dateFormat.format(date);
	}
%>
<br>
 <h1>

 <%= message %>
</h1> 

</body>
</html>