package exception;

import java.io.*;

class Master {
      String doFileStuff() throws FileNotFoundException { return "a"; }
}

class Exceptions2 extends Master {
      public static void main(String[] args) {
	String s = null;
	try { 
                          s = new Exceptions2().doFileStuff();
	} catch ( Exception x) { s = "b"; }
	System.out.println(s);         
      }
 
     String doFileStuff() throws NumberFormatException,FileNotFoundException { 
           return "b"; 
     }

}