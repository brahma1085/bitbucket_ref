class GrandParent  {
     int a = getA();

     public GrandParent() {
          System.out.println("GrandParent Class Constructor");
    }

    static {
          System.out.println("GrandParent Class Static Initializer");
    } 

    private int getA()  {
           System.out.println("GrandParent Class Member Initializer");
          return 0;
   }
}

class Parent  extends GrandParent{
     int a = getA();

     public Parent() {
          System.out.println("Parent Class Constructor");
    }

    static {
          System.out.println("Parent Class Static Initializer");
    } 

    private int getA()  {
           System.out.println("Parent Class Member Initializer");
          return 0;
   }
}

class Child extends Parent {
    int b = getB(); 

    static {
           System.out.println("Child Class Static Initializer");
    }
    static int ii=1;

    public Child() {
           System.out.println("Child Class Constructor");
    }

    private int getB() {
           System.out.println("Child Class Member Initializer");
           return 0;
    }
}

public class Initializer2 {
    public static void main(String[] args) {
           int i= Child.ii;
           //Child c = new Child();
          // System.out.println("Done" + c.b);
   }
}
