public class Master{
    boolean bContinue=false;
    public static void main(String argv[]){
	Master m = new Master();
	m.go();
    }
    public void go(){
	Slave s = new Slave(this);
	Thread t1 = new Thread(s);
	t1.start();
	while(bContinue==false){
	}
	s.setPrice(200);
    }
}

class Slave implements Runnable{
    int iPrice =100;
    Master master;
    Slave(Master m){
	master=m;
    }
    synchronized public void setPrice(int iM){
	iPrice=iM;	
    }

    synchronized public void run(){
	master.bContinue=true;
	while(true){
	    System.out.println(iPrice);
	}

    }
}

