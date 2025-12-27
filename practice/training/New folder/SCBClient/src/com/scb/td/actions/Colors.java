package com.scb.td.actions;

import java.awt.Color;

public class Colors
{
     
    public static Color setButtonColor()
    {
        return new Color(253,254,242);
    }
    
    public static Color setComboColor()
    {
        return new Color(253,254,242);
    }
    
    public static Color setMainPanelColor()
    {
        return new Color(253,254,233);
    }
    
    public static Color setSubPanelColor()
    {
        return new Color(232,255,238);
    }
    public static Color getDisabledFieldColor()
    {
        return Color.black;
    }
    public static String[] getFontSize(){
    	String font_size[]=new String[5];
    	font_size[0]="10";
    	font_size[1]="12";
    	font_size[2]="14";
    	font_size[3]="16";
    	font_size[4]="18";
    	return font_size;
    }
    
}
