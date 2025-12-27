package com.scb.td.actions;

import java.math.BigDecimal;

public class DoubleFormat {

	public static String doubleToString(double value,int scale){
		try {
			BigDecimal dec = new BigDecimal(value);
			return (dec.movePointRight(scale+1).setScale(1,0).movePointLeft(3).setScale(scale,1).toString());
		} catch (Exception exe) {
			return "0";
		}
	}
	
	public static String toString(double value){
		try {
			BigDecimal dec = new BigDecimal(value);
			int scale=2;
			return (dec.movePointRight(scale+1).setScale(1,0).movePointLeft(3).setScale(scale,1).toString());
		} catch (Exception exe) {
			return "0";
		}
	}
	
	public static double doublePrecision(double value,int scale){
		try {
			BigDecimal dec = new BigDecimal(value);
			return (dec.movePointRight(scale+1).setScale(1,0).movePointLeft(3).setScale(scale,1).doubleValue());
		} catch (Exception exe) {
			return 0;
		}
	}
}
