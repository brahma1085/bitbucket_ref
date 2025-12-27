/*
 * Created on Jul 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package SRC.COM.SUNRISE.CLIENT;

import java.awt.Color;

/**
 * @author J.ShivaKumaar
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
