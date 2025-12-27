package classes;

class Child extends Overriding5 {
       int i = 5;
       static int j = 4;

       public void say(String s){   //ok, private method can be shadowed
	   System.out.println("Child is saying " + i + " " +k); 
       }

       //final void tell() {   //compile err: cannot override final
	//  System.out.println("Child is telling");
       //}

       //void call() {    //compile err: cannot override static
	//  System.out.println("Child is calling " + j);
       //}

       static void call() {   //ok, static method can be shadowed
	  System.out.println("Child is calling " + j);
       }
} 

class Overriding5 {
       int i = 15;
       static int j = 14;
       int k = 13;

       private void say(String s){
	  System.out.println("I'm saying: " + s + " " + i);
       }

       final void tell() {
	  System.out.println("I'm telling");
       }

       static void call() {
	  System.out.println("I'm calling " + j);
       }

       public static void main(String[] args) {
           Overriding5 p = new Child();
	   p.say("Hello");    // I'm saying: Hello
           p.call();          // I'm telling
           ((Child)p).say("Hello");   // Child is saying 5 13
           ((Child)p).call();  // Child is calling 4
       }  
}

 /* final, static and private methods are not overridden by a subclass, hence are not overriden and 
results in calling the says method of Overriding5 class.   However, a static method can be hidden 
by another static method in a subclass with the same signature. Also, a private method can be hidden
by another method in the subclass with the same signature. However, DML cannot be used in these
cases. */