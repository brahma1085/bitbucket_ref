public class BriarMill extends Thread{
    public static void main(String argv[]){
	BriarMill bm = new BriarMill();
	bm.go();
    }
    public void go(){
	start();
    }
    public  void payBill(){
	try{
	    wait();
	}catch(InterruptedException ie){}
	System.out.println("done");

    }
    public void run(){
	payBill();
	notifyAll();
    }

} 

