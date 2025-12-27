package threads;

class XYZ extends Thread {
    ThreadTest1 demo;
    XYZ(ThreadTest1 td) {
           demo=td;
     }
     public void run() {
          demo.someMethod();
     }
}

public class ThreadTest1 {
      private int count = 1;

      public synchronized void someMethod() {
           for(int i=0;i<10;i++) {
                System.out.println(count++);
          }
      }

      public static void main(String[] args) throws Exception  {
          ThreadTest1 demo = new ThreadTest1();
          Thread a1 = new XYZ(demo);
          Thread a2 = new XYZ(demo);
          a1.start();
          a2.start();
      }
}

// result: 1-20 sequentially