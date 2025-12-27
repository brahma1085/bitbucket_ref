package threads;

class Task1 implements Runnable {
   public void run() {
       synchronized(Task1.class){
          synchronized(Task2.class){}
       }
   }
}

class Task2 implements Runnable {
   public void run() {
	synchronized(Task2.class){
           synchronized(Task1.class){}
        } 
   }
}

public class DeadLockMaybe1 {
   public static void main(String[] args) {
     new Thread(new Task1()).start();
     new Thread(new Task2()).start();
   }
}

//结果是--不确定的！可能deadlock，并且结果和当前Thread的执行机的环境有关！