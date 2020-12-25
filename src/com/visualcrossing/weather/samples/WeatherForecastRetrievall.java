package com.visualcrossing.weather.samples;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;   
import java.io.*; 

import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject ;

import javax.script.*;



@WebServlet("/WeatherForecastRetrievall")
public class WeatherForecastRetrievall extends HttpServlet
{
	
private static final long serialVersionUID = 1L;
    
    public WeatherForecastRetrievall() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	
	
	protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response) 
throws ServletException, IOException 
{ 
	
	
    
 
	String abc = request.getParameter("fm");
	String def = request.getParameter("no");
	System.out.println(abc);
	System.out.println(def);
	
	String fgh="\""+abc+","+def+"\"";
	
	try {
	
		URIBuilder builder = new URIBuilder("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/weatherdata/forecast");
		
		
		builder.setParameter("aggregateHours", "24")
			.setParameter("contentType", "json")
			.setParameter("unitGroup", "metric")
			.setParameter("locationMode", "single")
			.setParameter("key", "Z7RB9GS1WCMWEDREBP9RKAJXI")
			.setParameter("locations", fgh);

		HttpGet get = new HttpGet(builder.build());
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		CloseableHttpResponse responsible = httpclient.execute(get);    
		
		JSONObject jsonWeatherForecast = null;
		try {
			if (responsible.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.printf("Bad response status code:%d%n", responsible.getStatusLine().getStatusCode());
				
			}
			
			HttpEntity entity = responsible.getEntity();
		    if (entity != null) {
		    	String rawResult=EntityUtils.toString(entity, Charset.forName("utf-8"));
		    	
		    	jsonWeatherForecast = new JSONObject(rawResult);
		    	
		    }
		    
		    
		} finally {
			responsible.close();
		}
		
		if (jsonWeatherForecast==null) {
			System.out.printf("No weather forecast data returned%n");
			
		}
		JSONObject location=jsonWeatherForecast.getJSONObject("location");
		System.out.printf("Weather forecast for: %s%n", location.getString("address"));
		JSONArray values=location.getJSONArray("values");
		
		response.setContentType("text/html");//setting the content type  
		PrintWriter pw=response.getWriter();//get the stream to write the data  
		pw.println("<html>");
		//pw.println("<body> <input type='button' onclick='tableFromJson()'  value='Create Table from JSON data' /></body>");
		
		System.out.printf("Date\tMaxTemp\tMinTemp\tChangeofPrecip%n");
		
		
	  
		double[][] abcd = new double[3][3];
		String[] efgh=new String[3];
		for (int i = 0; i < 3; i++) {
			JSONObject forecastValue = values.getJSONObject(i);
		
           String datetimeString=forecastValue.getString("datetimeStr");
            
            ZonedDateTime datetime=ZonedDateTime.parse( datetimeString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            
            double  maxtemp=forecastValue.getDouble("maxt");
            double mintemp=forecastValue.getDouble("mint");
           
            double pop=forecastValue.getDouble("pop");
            efgh[i]=datetime.format(DateTimeFormatter.ISO_LOCAL_DATE);
            
            abcd[i][0]= maxtemp;
            abcd[i][1]=mintemp;
            abcd[i][2]=pop;
            
            
          System.out.printf("%s\t%.1f\t%.1f\t%.0f%n", datetime.format(DateTimeFormatter.ISO_LOCAL_DATE), maxtemp, mintemp, pop);
         // System.out.printf("%s\t%.1f\t%.1f\t%.0f%n", efgh[i],abcd[i][0] ,abcd[i][1], abcd[i][2]);
		} 
		int a=10;
		request.setAttribute("kom", a);
		request.setAttribute("tom", abcd);
		request.setAttribute("som", efgh);
		//response.sendRedirect("ads.jsp");                  
		RequestDispatcher dispatcher = request.getRequestDispatcher("ads.jsp");
		//request.setAttribute("abcd", "efgh"); // set your String value in the attribute
		dispatcher.forward( request, response );
		
		
		
	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    
}   

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		processRequest(request, response); 
			
		}
	}
		  
	
	