package com.edu;

enum Month {
	JAN(1),FEB(2),MAR(3),APR(4),MAY(5),JUN(6),JUL(7),AUG(8),SEP(9),OCT(10),NOV(11),DEC(12);
	private final int value;
	Month(int a){
		value=a;
	}
	public int getValue(){
		return value;
	}
}
public class Demo2{
	public static void main(String...args){
		System.out.println(Month.JAN.getValue());
		System.out.println(Month.FEB.getValue());
		System.out.println(Month.MAR.getValue());
		System.out.println(Month.APR.getValue());
		System.out.println(Month.JUN.getValue());
		System.out.println(Month.JUL.getValue());
		System.out.println(Month.AUG.getValue());
		System.out.println(Month.SEP.getValue());
		System.out.println(Month.OCT.getValue());
		System.out.println(Month.NOV.getValue());
		System.out.println(Month.DEC.getValue());
	}
}