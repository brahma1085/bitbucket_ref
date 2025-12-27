package exception;

import java.io.*;

class Exception4 {
   static void method() throws Exception{
	throw new EOFException();
   }

   public static void main(String args[]){
	try{
	   method();
	}catch(Exception e){}   //ok
        //}catch( EOFException e){} // compiler error
   }
}

/*
Which of the following should be placed at //// so that
the code will compile without errors?
A. IOException e
B. EOFException e
C. MalformedURLException e
D. NullPointerException e
E. Exception e
F. FileNotFoundException
*/