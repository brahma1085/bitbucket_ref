package threads;

class Waiting3 implements Runnable {
    int state;
    public synchronized void run() {
        if (state++ < 3) { 
	System.out.print(" " + Thread.currentThread().getId());
	try { this.wait(); } catch (Exception e) { }
 	System.out.print(" " + Thread.currentThread().getId()); 
        }
        else {
	 try { Thread.sleep(2000); } catch (Exception e) { }
	 notify(); 
	 notifyAll(); 
        }
}

public static void main(String [] args) {
    Waiting3 w1 = new Waiting3();
    Waiting3 w2 = new Waiting3();
    new Thread(w1).start();
    new Thread(w1).start();
    new Thread(w2).start();
    new Thread(w2).start();
    }

}
