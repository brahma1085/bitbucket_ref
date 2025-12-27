package com.ge.crd.ementoring.util.Mentor;

import java.util.Calendar;

public class MonthList { 
	
	public MonthList() {}
	
	public static String getMonthList() throws Exception {
		String[]months = {"January","February","March","April","May","June","July","August","September","October","November","December"};				
		String monthList="";					
		 for(int j=0;j<months.length;j++) { 
			monthList = monthList + "<option value='"+(j+1)+"'>"+months[j]+"</option>";
		 }		 
		return monthList;
	}
	
	public static String getYearList() throws Exception {
	   Calendar cal=Calendar.getInstance(); 
	   String yearList ="";
		 for(int i=(cal.get(Calendar.YEAR)); i > 1950; i--) { 
			yearList = yearList + "<option value='"+i+"'>"+i+"</option>";					  				  
		  }   		 
		return yearList;
	}
	
	public static String getMonthList(int montNo) throws Exception {
		String[]months = {"January","February","March","April","May","June","July","August","September","October","November","December"};				
		String monthList="";					
		 for(int j=0;j<months.length;j++) {
			 if(montNo ==(j+1)) {
				 monthList = monthList + " <option value='"+(j+1)+"' selected>"+months[j]+"</option>";
			 } else {
				 monthList = monthList + " <option value='"+(j+1)+"'>"+months[j]+"</option>";
			 }
		 }		 
		return monthList;
	}
	
	public static String getYearList(int yearNo) throws Exception {
	   Calendar cal=Calendar.getInstance(); 
	   String yearList ="";
		 for(int i=(cal.get(Calendar.YEAR)); i > 1950; i--) {			 			 
			 if(yearNo == i) {
				 yearList = yearList + "<option value='"+i+"' selected>"+i+"</option>";					  				  
			 } else {
				 yearList = yearList + "<option value='"+i+"'>"+i+"</option>";
			 }
		  }   		 
		return yearList;
	}	
}



