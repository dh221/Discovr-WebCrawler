package com.discovr.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;

public class SingleEvent {

	
	String name;
	String host;
	String location;
	String description;
    String start_time;
    String end_time;
	
	/*
	 * Converting the html text into an event object
	 */
	void htmlToEvent(String eventInput, String day, String month, String year) throws ParseException{
		
		  String input = eventInput; 
		  String time;
		  String s1 = "Time: ";
		  String s2 = "Location: ";
		  String s3 = "Calendar: ";
		  String s4 = "Description: ";
		  String [] correctTime;
	      String[] item1 = input.split(s1);
	      checkBound(item1);
	      String[] item2 = item1[1].split(s2);
	      checkBound(item2);
	      String[] item3 = item2[1].split(s3);
	      checkBound(item3);
	      String[] item4 = item3[1].split(s4);
	      checkBound(item4);
	        
	        name = deDup(item1[0]);
	        name = correctName(name).trim();
	        time = item2[0];
	        if(time.trim().equals("all day")){
	        	time = "8:00 AM - 5:00 PM";
	        }
	        correctTime = correctTime(time);
	        host = item4[0].trim();
	        location = item3[0].trim();
	        description = item4[1].trim();
	        start_time = year+"-"+month+"-"+day+"T"+correctTime[0];
	        end_time = year +"-"+ month+"-"+day+"T"+correctTime[1];
	        System.out.println("Event: " + name);
	       /* 
	        System.out.println("Event: " + name);
	        System.out.println("Date: " + date);
	        System.out.println("Address: " + location);
	        System.out.println("Host: " + host);
	        System.out.println("Description: " + details);
	        System.out.println("");
			*/   
	}
		
    String [] correctTime(String time) throws ParseException {
		// TODO Auto-generated method stub
    	
    	String [] returnTime = {"8:00","18:00"};
    	
    	if(time.contains("-")){
    	 returnTime = time.split(" - ");
    	 returnTime[0].trim();
    	 returnTime[1].trim();
    	 SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
    	 SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
    	 returnTime[0] = date24Format.format(date12Format.parse(returnTime[0]));
    	 if(returnTime[1].length() >= 8)
    	 returnTime[1] = "11:59 PM";
    	 returnTime[1] = date24Format.format(date12Format.parse(returnTime[1]));
    	}
    	else{
    		SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
       	    SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
    		returnTime[0] = date24Format.format(date12Format.parse(time));
    		returnTime[1] = "23:59";
    	}

		return returnTime;
	}

/*
    * Re-construct the string we split to ensure that we pass the correct string for the next split 
    */
   void checkBound(String [] currentItem){
	   
	   int index;
	   int size = currentItem.length;
	   if(currentItem.length > 2){
		
		   for(index = 2; index < size; index++){
			   currentItem[1] = currentItem[1] + currentItem[index];
		   }		   
		   
	   }
	   
   }
   
   public String deDup(String s) {
	    return new LinkedHashSet<String>(Arrays.asList(s.split("-"))).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", "-");
	}
   
   /*
    * Correcting the event name by removing confusing characters and duplicated words
    */
   public String correctName(String duPname){
		
	    String returnName = duPname;
		if(duPname.contains("PM:")){
			String[] itemX = duPname.split("PM: ");
			returnName =  itemX[itemX.length-1];
			returnName = returnName.substring(returnName.length()/2);
		}
		
		else if(duPname.contains("AM:")){
			String[] itemX = duPname.split("AM: ");
			returnName =  itemX[itemX.length-1];
			returnName = returnName.substring(returnName.length()/2);
		}
		
		else if(duPname.contains("(cont)")){
			String[] itemX = duPname.split("(cont)");
			returnName =  itemX[itemX.length-1];
			returnName = returnName.substring(2);
			returnName = returnName.substring(returnName.length()/2);
		}
		
		else if(duPname.contains("all day:")){
			String[] itemX = duPname.split("all day: ");
			returnName =  itemX[itemX.length-1];
			returnName = returnName.substring(returnName.length()/2);
		}
		return returnName;
		
	}
	
	void create(String eventname, String urLaddress, String time, String eventLocation, String creator){
		
		
		location = eventLocation;
		host = creator; 
		name = eventname;
	}
	
}
