package com.scb.fc.forms;

public class Check {

	
	Check()
	{
		System.out.println("Constructor");
		
	}
	{
		System.out.println("Default");
		
	}
	
	
	public static void main(String[] ar){
		
		 Check check = new Check();
		 System.out.println("hi in main");
		
	}
	 static{
			System.out.println("Hello i m in static");
		}
}
