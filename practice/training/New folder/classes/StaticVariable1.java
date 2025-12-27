package classes;

class Uber {
     static int y = 2;
     Uber(int x) { this(); y = y * 2; }
     Uber() { y++; }
}

class StaticVariable1 extends Uber {
     StaticVariable1() { super(y); y = y + 3; }   // static variable can be inherited

     public static void main(String [] args) {
             new StaticVariable1();
             System.out.println(y); 
     } 
}