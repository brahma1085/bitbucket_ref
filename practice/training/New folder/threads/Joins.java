package threads;

import java.util.concurrent.*;

public class Joins {
     static Thread createThread(final int i,final Thread t1) {
	Thread t2=new Thread()  {
	       public void run()  {
		System.out.println(i+1);
		try {
		    t1.join();  // have to be put in try block, otherwise compiler err
		}
		catch(InterruptedException e) {}

                 	                  try {
                       	    TimeUnit.MILLISECONDS.sleep(1000);
                  	} catch(InterruptedException e) {}

		System.out.println(i+2);
	      }
    	};

     	System.out.println(i+3);
  	t2.start();
                 //try {
                 //     TimeUnit.MILLISECONDS.sleep(100);
                 //} catch(InterruptedException e) {}
     	System.out.println(i+4);
     	return t2;
    }

    public static void main(String[] args) {
                  Thread t = createThread(20,Thread.currentThread());

                  try {
                       TimeUnit.MILLISECONDS.sleep(1000);
                  } catch(InterruptedException e) {}

                  createThread(10, t);
	//createThread(10,createThread(20,Thread.currentThread()));
    }
}
