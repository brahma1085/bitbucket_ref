package threads;

public class Controller {
    public static void main(String args[]) {
            // JVM will have only one instance of SyncData, i.e data
           SyncData data = SyncData.getInstance();
           Worker w1 = new Worker(data, true);
           w1.kickStart();
           Thread.yield();

           Worker w2 = new Worker(data, false);
           w2.kickStart();
           Thread.yield();
           
           while(true) {
	if(data.inSync()) {
	       System.out.println("In Sync, i = " + data.getI() + " and j = " + 
                                          data.getJ() + " continuing...");   
	}
	else {
	       System.out.println("Not in Sync, i = " + data.getI() + " and j = " + data.getJ());
	       w1.terminate();
	       w2.terminate();
	       break;
	}
          }
    }
}

class Worker implements Runnable {
           private SyncData data;
           private boolean forward;
           private Thread theThread;

           public Worker(SyncData data, boolean forward) {
	this.data = data;
	this.forward = forward;
           }

           public void kickStart() {
	if(theThread==null) {
	           theThread=new Thread(this);
	           theThread.start();
           	}
           }

           public void terminate() {
	 theThread=null;
           }

           public void run() {
	double d = 0.0D;
	while(theThread==Thread.currentThread()) {
	           if  (forward) {
	                d = d + 1;
	           }
	           else { d = d - 1; }
	           this.data.sync(d);
	}
           }
}

//Its a singleton, to make sure JVM has only one instance of it.
class SyncData {
           private static SyncData data;
           private SyncData() {}

           public static SyncData getInstance() {
	if(data == null) {
	           data = new SyncData();
	}
           	return data;
           }

           private double i = 0;
           private double j = 0;

           // Makes sure that i and j are always in sync.
           // This is synchronized to make sure i and j are always in sync.
           // But doesn't seem to work.
           // What am I missing? because  inSync() should be synchronized
           public void sync(double syncValue) {
	synchronized (this) {
	           System.out.println("Sync called with " + syncValue);
	           this.i = syncValue;
	           // Kill some time. Don't yeild.
	           for(int x=0; x<1000; x++) {
		 new Object();
	           }
           	           this.j = syncValue;
                 }
           }

           public synchronized boolean inSync() {  return i==j; }   // this should be synchronized
           public double getI() {   return i;  }
           public double getJ() {   return j;  }
} 