package threads;

class MyThread extends Thread {
    MyThread(){}
    MyThread(Runnable r) {super(r);}
    public  void run() {System.out.print("Inside thead");}
}

class MyRunnable implements Runnable {
    public void run() {System.out.print("Inside runnable");}
}

public class Test {
    public static void main(String[] args){
        new MyThread().start();
        new MyThread(new MyRunnable()).start();
    }
}