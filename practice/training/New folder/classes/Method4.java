package classes;

import java.io.IOException;

public class Method4 {
     void f(int n) {
          System.out.println("f("+n+")");
          if (n>0) f(--n);
     }

     public void methodA() throws IOException { //Must throws checked exception here
          throw new IOException(); 
     } 


     public static void main(String[] args) {
           Method4 m = new Method4();
           m.f(3);

           try{ 
                m.methodA(); 
           }catch(IOException e){ 
    	        System.out.println("Caught Exception"); 
           } 
     }
}