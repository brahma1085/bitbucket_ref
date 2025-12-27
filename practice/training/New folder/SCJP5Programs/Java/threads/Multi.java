package thread;

class TSamp extends Thread{
    public native String getTime();
  
}

public class Multi implements Runnable {
    boolean bStop;
    public static void main(String argv[]){
	Multi m = new Multi();
	m.go();
    }
    public void go(){
	TSamp ts = new TSamp(this);
	ts.start();
	bStop=true;
	
    }
    public void run(){
	if(bStop==true){
	    return;
	}
	System.out.println("running");
    }

}
