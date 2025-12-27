package exception;

import java.io.*;

class Tests {
    public static void main(String[] args) {
         //testFile();
         System.out.println(testRuntimeException());  // output "Peace"
    }

    static String testRuntimeException(){
	 try{
              throw new Exception();
	 }
         catch (Exception e){
              int i = 1 / 0 ;
              return "Error";
         }
	 finally  {
              return "Peace";
	 }
    }

    static void testFile() {
         try{
              File f = new File("test1.txt");
              BufferedReader brf = new BufferedReader(new FileReader(f));
              String s = brf.readLine();
         }catch(java.io.IOException e){}
    }

    static int testRuntimeException2( char a) {
	 throw new NumberFormatException();  //compilation is ok
    }

}