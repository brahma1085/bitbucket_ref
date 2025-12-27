package classes;

class Clidders {
     public final void flipper() { System.out.println("Clidder"); }
     //public static void flipper() { System.out.println("Clidder"); }
}

public class Final1 extends Clidders {
     /* Compile-error: final or static method cannot be overridden.
     public void flipper() {
	System.out.println("Flip a Clidlet");
	super.flipper();
     }
     */

     public void flipper(int i) {
	System.out.println("Flip a Clidlet");
	//super.flipper();
                 flipper();
     }

     public static void main(String [] args) {
	new Final1().flipper();   // output: Clidder
                 new Final1().flipper(1); //output: Flip a Clidlet \n Clidder
    }
}