<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.visualcrossing.weather.samples.WeatherForecastRetrievall"%> 
<%@page import="java.util.*"%> 


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<head>
<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}
th, td {
  padding: 15px;
}
</style>
</head>

<table style="width:100%">
  <tr>
    <th>  Date  </th>
    <th>MaxTemp</th>
    <th>MinTemp</th>
    <th>ChangeofPrecip</th>
  </tr>


<%  
   double[][] lmn =  (double[][])request.getAttribute("tom"); 
  String[] opq=(String[])request.getAttribute("som");     
   for(int i=0;i<3;i++)
        {%>
             <tr>
                <td><%= opq[i] %></td>
                <td><%= lmn[i][0] %></td> 
                <td><%= lmn[i][1] %></td>   
                <td><%= lmn[i][2] %></td> 
     
            </tr> 
      <%} %>
 
 

 
 

  
</table>


</body>
</html>