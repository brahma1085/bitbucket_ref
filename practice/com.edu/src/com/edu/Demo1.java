package com.edu;

import java.util.Scanner;

enum Day {
	MON,TUE,WED,THU,FRI,SAT,SUN
}
public class Demo1{
	public static void main(String...args){
		String s="";
		Scanner sc=new Scanner(System.in);
		System.out.println("enter your favourite day:"+s);		
		s=sc.nextLine();
		s=s.toUpperCase();	
		Day d=Day.valueOf(s);
		fun(d);
	}
	private static void fun(Day d) {
			switch(d){
			case MON:
				System.out.println("shiva puja");
				break;
			case TUE:
				System.out.println("hunuman puja");
				break;
			case WED:
				System.out.println("ganesh puja");
				break;
			case THU:
				System.out.println("baba puja");
				break;
			case FRI:
				System.out.println("laxmi puja");
				break;
			case SAT:
				System.out.println("venky puja");
				break;
			case SUN:
				System.out.println("enjoy holiday");
				break;
			}
	}
}
