package threads;

public class Threads implements Runnable {
      private int x; 
      private int y; 

      public static void main(String[] args) {
          Threads that = new Threads(); 
          (new Thread(that, "first")).start(); 
          (new Thread(that, "second")).start(); 
      }

      public void run()  { 
	   for (;;) {
		x++; 
		y++; 
		//System.out.println(Thread.currentThread().getName() + "x=" + x + ", y = " + y); 
                assert(x==y): "x=" + x + ", y = " + y;
	   } 
      }
} 
