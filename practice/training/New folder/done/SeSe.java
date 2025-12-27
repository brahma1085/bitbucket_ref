public class SeSe implements Runnable{
    public static void main(String argv[]){
	SeSe s = new SeSe();
	s.go();
    }
    public void go(){
	Thread t = new Thread(this);
                  t.start();
    }
    public void run(){
	System.out.println("run");
    }
    
}

