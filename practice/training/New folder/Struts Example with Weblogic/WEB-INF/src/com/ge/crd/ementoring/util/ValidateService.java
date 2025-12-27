package com.ge.crd.ementoring.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateService {
	
    public static boolean isFloat(String sTextString) {

        boolean bResult = false;

		Pattern p = Pattern.compile("(\\d){1,7}(.(\\d){1,4})?");
        Matcher m = p.matcher(sTextString);
        bResult = m.matches();

        return bResult;

    }

    public static String setLimit(String sTextString, int iLength) {

        sTextString = ( sTextString == null ) ? "" : sTextString.trim();

        if ( sTextString.length() > iLength ) {
            sTextString = sTextString.substring(0, iLength);
        }

        return sTextString;

    }

    public static boolean isEmpty(String sTextString) {

        sTextString = ( sTextString == null ) ? "" : sTextString;
        return (sTextString.trim().length() < 1);

    }

    public static String doConvert(String sTextString) {

        String sResult = null;

        Pattern p = Pattern.compile("a*-");  //"^(n)+$");
        Matcher m = p.matcher(sTextString);
        sResult = m.replaceAll("<br>");

        return sResult;

    }


	public static boolean isValidString(String sTextString) {

		boolean bResult = false;

		Pattern p = Pattern.compile("^[a-zA-Z0-9 \\_\\-\\n\\r:;(@)/?.,\\']+$");  //"^[[\\w\\s\\p{Punct}]*[a-zA-z0-9\\%]+[\\w\\s\\p{Punct}]*]{1,100}$");
		Matcher m = p.matcher(sTextString);
		bResult = m.matches();

		return bResult;
	}

    public static boolean isValidEmail(String sTextString) {

        boolean bResult = false;

        Pattern p = Pattern.compile("^[\\w]*[_\\-\\.\\w]*\\@[\\w\\-]+\\.?[\\w\\-\\._]*$");
        Matcher m = p.matcher(sTextString);
        bResult = m.matches();

        return bResult;

    }

      public static boolean isAlphaBNumeric(String sTextString) {

        boolean bResult = false;

        Pattern p = Pattern.compile("^[a-zA-Z0-9 ,\\-&.\\']+$");
        Matcher m = p.matcher(sTextString);
        bResult = m.matches();

        return bResult;

    }

    public static boolean isAlphaB(String sTextString) {

        boolean bResult = false;

        Pattern p = Pattern.compile("^[a-zA-Z ,\\-&.\\']+$");
        Matcher m = p.matcher(sTextString);
        bResult = m.matches();

        return bResult;

    }

    public static boolean isAlpha(String sTextString) {

        boolean bResult = false;

        Pattern p = Pattern.compile("^[a-zA-Z &.\\']+$");
        Matcher m = p.matcher(sTextString);
        bResult = m.matches();

        return bResult;

    }

    public static boolean isAlphaNumeric(String sTextString) {
        boolean bResult = false;

        Pattern p = Pattern.compile("^[a-zA-Z0-9 .\\-,\\']+$"); //"^[\\w]$");
        Matcher m = p.matcher(sTextString);
        bResult = m.matches();

        return bResult;
    }
    
    public static boolean isValidValue(String sTextString) {
    	boolean bResult = false;

    	//String alphaNumericPattern = " \'#*%&\\_-+=~:;{}[]|?(@)/?.,$-";
    	String alphaNumericPattern = "a-zA-Z0-9!@#$%^&*()_-+=[]|\\:;\"\'?/.,~`" ;
    	Pattern p = Pattern.compile(alphaNumericPattern); 
    	Matcher m = p.matcher(sTextString);
    	bResult = m.matches();
    	
    	return bResult;
    	
    }

    public static boolean isNumeric(String sTextString) {

        boolean bResult = false;

        Pattern p = Pattern.compile("^[0-9.]+$");
        Matcher m = p.matcher(sTextString);
        bResult = m.matches();

        return bResult;

    }

    public static final int MDY = 1;
    public static final int DMY = 2;

    public static boolean isValidDate(int iType, String strDate) {

        String date = setLimit(strDate.trim(), 10);
        int[] monthArray = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int iYear, iMonth, iDate;

        if ( date.length() != 10 ) {
            return false;
        }

        //Year Check
        iYear = Integer.parseInt(date.substring(6,10));
        if ( iYear % 100 == 0 ) {
            if ( iYear % 400 == 0 ) monthArray[1] = 29;
        }
        else {
            if ( iYear % 4 == 0 ) monthArray[1] = 29;
        }


        //Month Check
        if ( iType == MDY ) {
            iMonth = Integer.parseInt(date.substring(0,2));
        }
        else if ( iType == DMY ) {
            iMonth = Integer.parseInt(date.substring(3,5));
        }
        else {
            return false;
        }
        //int iMonth = Integer.parseInt(date.substring(0,2));
        if ( iMonth < 1 || iMonth > 12 ) {
            return false;
        }


        //Date Check
        if ( iType == MDY ) {
            iDate = Integer.parseInt(date.substring(3,5));
        }
        else if ( iType == DMY ) {
            iDate = Integer.parseInt(date.substring(0,2));
        }
        else {
            return false;
        }

        if ( iDate > monthArray[iMonth-1] ) {
            return false;
        }

        return true;
    }


    public static boolean isValidDate(String strDate) {

        String date = setLimit(strDate.trim(), 11);
        int[] monthArray = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        String[] MonthNames = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int iYear, iMonth, iDate;

		if ( date.length() != 11 ) {
            return false;
        }


        //Year Check
		try {
	        iYear = Integer.parseInt(date.substring(7, 11));
		}
		catch (Exception ex) {
			return false;
		}

        if ( iYear % 100 == 0 ) {
            if ( iYear % 400 == 0 ) monthArray[1] = 29;
        }
        else {
            if ( iYear % 4 == 0 ) monthArray[1] = 29;
        }


        //Month Check
		String sMonth = date.substring(3, 6);
		iMonth = -1;
		for(int i=0; i<MonthNames.length ; i++) {
			if ( MonthNames[i].toUpperCase().equals(sMonth.toUpperCase()) ) {
				iMonth = (i + 1);
				break;
			}
		}
		if ( iMonth < 1 || iMonth > 12 ) {
            return false;
        }

        //Date Check
		try {
			iDate = Integer.parseInt(date.substring(0,2));
		}
		catch (Exception ex) {
			return false;
		}

		if ( iDate < 1 ) return false;

        if ( iDate > monthArray[iMonth-1] ) {
            return false;
        }

        return true;
    }

    public static Date getDateObject(String sDate) {

        String date = sDate.trim();
        String[] MonthNames = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int iYear, iMonth, iDate;

		String sMonth = date.substring(3, 6);
		iMonth = -1;
		for(int i=0; i<MonthNames.length ; i++) {
			if ( MonthNames[i].toUpperCase().equals(sMonth.toUpperCase()) ) {
				iMonth = i;
				break;
			}
		}

		iYear = Integer.parseInt(date.substring(7, 11));
		iDate = Integer.parseInt(date.substring(0,2));

        Date objDate = new Date();
        objDate.setDate(iDate);
        objDate.setMonth(iMonth);
        objDate.setYear(iYear - 1900);

        return objDate;

    }

    public static boolean isValidTravelDate(String sFirstDate, String sSecondDate) {

        Date obj1 = null;
        if ( sFirstDate != null ) {
            obj1 = getDateObject(sFirstDate);
        }
        else {
            obj1 = new Date();
        }

        Date obj2 = getDateObject(sSecondDate);

		return (! obj2.before(obj1));

    }


    public static int getDateDiff(String sFirstDate, String sSecondDate) {

        Date obj1 = getDateObject(sFirstDate);
        Date obj2 = getDateObject(sSecondDate);

        long diff = obj2.getTime() - obj1.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));

    }

    public static String[] toStringArray(ArrayList arrList) {
        String[] data = new String[arrList.size()];

        for(int i=0; i<data.length ; i++) {
            data[i] = (String) arrList.get(i);
        }

        return data;
    }
    
}
