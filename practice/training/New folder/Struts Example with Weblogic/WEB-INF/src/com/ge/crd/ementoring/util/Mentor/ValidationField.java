package com.ge.crd.ementoring.util.Mentor;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

public class ValidationField implements java.io.Serializable {

	
	public static boolean isValid(String sData) {
	    
        sData = limitLength(sData);

        if ( sData.indexOf("<") > 0 ) {
                        return false;
        }

        if ( sData.indexOf(">") > 0 ) {
                        return false;
        }

        if ( sData.indexOf("</") > 0 ) {
                        return false;
        }

        if ( sData.indexOf("alert") > 0 ) {
                        return false;
        }

        return true;
}


public static boolean validateInput(String inputString){
 boolean found =false;
 CharacterIterator it = new StringCharacterIterator(inputString);
 String alphaNumericPattern = ".'_|%- ";
    for (char ch=it.first(); ch != CharacterIterator.DONE; ch=it.next()) {
        // 47 to 58 (/,0,1.... 9,:)
        // 64 to 91 (@,A,B,....Z,[ )
        // 96 to 123 (',a,b,....z,{ )
        if((ch >47 && ch<58) || (ch>64 && ch <91) || (ch>96 && ch <123) || (ch == 44)) {
            continue;
        }
    found = false;
    for(int i=0;i<alphaNumericPattern.length();i++) {
        if(ch==alphaNumericPattern.charAt(i)) {
           found=true;
           break;
        }
    }
      if(found==false)
         return false;
   }
  return true;
}

    public static boolean validateInput1(String inputString){
     boolean found =false;
     CharacterIterator it = new StringCharacterIterator(inputString);
     String alphaNumericPattern = "_ ";
        for (char ch=it.first(); ch != CharacterIterator.DONE; ch=it.next()) {
            if((ch >47 && ch<58) || (ch>64 && ch <91) || (ch>96 && ch <123) || (ch == 44) ) {
                continue;
            }
        found =false;
        for(int i=0;i<alphaNumericPattern.length();i++){
            if(ch==alphaNumericPattern.charAt(i)){
               found=true;
               break;
            }
        }
            if(found==false)
            return false;
       }

     return true;
    }

/// Only String is allowed
/// return TRUE when all are string / return FALSE when is not String
public static boolean validateString(String inputString){
 boolean found =true;
  CharacterIterator it = new StringCharacterIterator(inputString);

  for (char ch=it.first(); ch != CharacterIterator.DONE; ch=it.next()) {
       // 65 to 90 (A,B,....Z)
       // 97 to 122 (a,b,....z)
        if((ch>64 && ch <91) || (ch>96 && ch <123 ) || (ch == 32) || (ch == 44) ) {
           continue;
        } else {
           found = false;
           break;
        }
   }
 return found;
}

     public static boolean validateNuemeric(String inputString){
     // 48 to 57 (0,1.... 9)
     CharacterIterator it = new StringCharacterIterator(inputString);
      for (char ch=it.first(); ch != CharacterIterator.DONE; ch=it.next()) {
            if(!(ch >47 && ch<58) || (ch==45) ){
               return false;
            }
       }
     return true;
    }


    public static boolean checkOptionItem(String strSelectedValue,ArrayList arrOptionItem) {
        boolean rtnResult = arrOptionItem.contains(strSelectedValue);
        return rtnResult;
    }

    // Validate the data format.....
    // This validation is only for MM/DD/YYYY format
    public static boolean validDate(String strDate) {

        String date = limitLength(strDate);
        int[] monthArray={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if(date.length()!=10) {
            return false;
        }

        int iYear = Integer.parseInt(date.substring(6,10));
        if ( iYear % 4 == 0 || iYear % 100 != 0 ) {
            monthArray[1] = 29;
        }

        int iMonth = Integer.parseInt(date.substring(0,2));
        if ( iMonth < 1 || iMonth > 12 ) {
            return false;
        }

        int iDate = Integer.parseInt(date.substring(3,5));
        if ( iDate > monthArray[iMonth-1] ) {
            return false;
        }


        return true;
    }

    public static String limitLength(String sData)
           {
               if ( sData == null ) {
                   sData = "";
               }
             return sData.trim();
           }
    
    // Return TRUE when string is Empty
    // Return FALSE when String is Not Empty
    public static boolean isEmpty(String sTextString) {
        sTextString = ( sTextString == null ) ? "" : sTextString;
        return (sTextString.trim().length() < 1);
    }
    
}
