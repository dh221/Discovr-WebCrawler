package com.discovr.crawler;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;

public class collectEvent {

	public static void main(String[] args) throws Exception
    {
		/*
		 * Retrieving today's date
		 */
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int monthOfYear = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		
		String startDay = String.valueOf(dayOfMonth);
		/*
		String endDay;
		if(dayOfMonth >= 28){
			endDay = "3";
		}
		else endDay = String.valueOf(dayOfMonth+2);
		*/
		
		//If the attribute we reading is longer a valid date, we don't collect data
		boolean collect = false;
		
		String currentDay = startDay;
		String currentMonth = String.valueOf(monthOfYear);
		String currentYear = String.valueOf(year);	
       
	
        String calendarURL = "http://calendar.events.ubc.ca/cal/main/showMain.rdo";   
        Document document = Jsoup.connect(calendarURL).timeout(0).get();
    	List<SingleEvent> collectedEvents = new ArrayList<SingleEvent>();
    	SingleEvent displayEvent = new SingleEvent();
    	
    	//Extracting data from the UBC Event's main calendar (week)
        for( Element element : document.select("a[href]"))
        {
           String toText = element.text().toString();
          
           if(toText.equals(startDay))
           collect = true;
  
          else if(toText.equals(""))
          collect = false;
           
           if(collect == true) {
        	   
          
		   if(toText.length() < 3){      
			currentDay = toText;	
			
           if(Integer.parseInt(currentDay.trim()) < dayOfMonth ){
        	     if(monthOfYear == 12){
        	    	year++;
        	    	monthOfYear = 1;
        	    	currentMonth= "1";
        	     }
        	     else {
        	    	monthOfYear++;
         	    	currentMonth= String.valueOf(monthOfYear);
        	     }
        	  }
		} 
           else if(toText.length() > 3){
        	//Adding a new single event to our event collection
        	 SingleEvent newEvent = new SingleEvent();
        	 newEvent.htmlToEvent(toText, currentDay,currentMonth, currentYear);
        	 collectedEvents.add(newEvent);
           }
            
            
     }
           
            
            
        }
        
       
        System.out.println("There are " + collectedEvents.size() + " events happening this week.");
        for(int index = 0; index < collectedEvents.size(); index++){
        	
        	displayEvent = collectedEvents.get(index);
        	String jsonEvent = new Gson().toJson(displayEvent);
        	System.out.println(jsonEvent);
          /*
        	System.out.println("Event: " + displayEvent.name);
 	        System.out.println("Date: " + displayEvent.date);
 	        System.out.println("Address: " + displayEvent.location);
 	        System.out.println("Host: " + displayEvent.host);
 	        System.out.println("Description: " + displayEvent.details);
 	        System.out.println("");
 	       */ 
 	      	
        }
        
       displayEvent = collectedEvents.get(1);
       String test = new Gson().toJson(displayEvent);
       uploadEvents.http(test);
        
        
    }
}
