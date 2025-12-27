class Parent  {
     int a = getA();

     public Parent() {
          System.out.println("Parent Class Constructor");
    }

    static {
          System.out.println("Parent Class Instance Initializer");
    } 

    private int getA()  {
           System.out.println("Parent Class Member Initializer");
          return 0;
   }
}

public class Initializer1 extends Parent {
    int b = getB(); 

    static {
           System.out.println("Child Class Instance Initializer");
    }

    public Initializer1() {
           System.out.println("Child Class Constructor");
    }

    private int getB() {
           System.out.println("Child Class Member Initializer");
           return 0;
    }

    public static void main(String[] args) {
           Initializer1 c = new Initializer1();
           System.out.println("Done" + c.b);
   }
}
