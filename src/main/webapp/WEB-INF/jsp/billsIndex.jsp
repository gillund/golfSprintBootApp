<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="UTF-8" />
      <title>Welcome</title>
    </head>
   <body>
      <h1>Welcome</h1>
      <h2>${message}</h2>
         
      <a href="${pageContext.request.contextPath}/personList">Person List</a>  
       
   </body>
   
</html>