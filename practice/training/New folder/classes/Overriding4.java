package classes;

class Phone {
      static String device = "Phone.device";
      void showDevice() {
      	System.out.print("Phone.showDevice," + device + " ");
      }

      Phone() {
       	showDevice();
      }
}

class Mobile extends Phone {
      String device = "Mobile.device";

      void showDevice() { // line (2)
	System.out.print("Mobile.showDevice," + device + " ");
      }

      Mobile() {
	// super(); First line: implicitly added by compiler, call to Phone'e constructor. // line (0)
	// device is initilized now.
	showDevice();
      }
}

public class Overriding4 {
      public static void main(String[] args) {
	Mobile n = new Mobile(); // line (1)
      	n.showDevice(); // line (3)
      }
}

/*
Well that's a good Question.

Read and understand each and every points before going to next one.
At the end everything makes sense.

1) Here there are two classes Phone(superclass) and Mobile(subclass).
This is how object initialization occures when you create an instance of subclass(ie Mobile).

First line of Child Constructor (invoked by "new")
First line of Child Constructor (invoked by "this")
First line of Parent Constructor(call to its superclass constructor,Object here)
Parent Class Initializers in the order of definition.
Remainder of Parent Constructor
Child Class Initializers in the order of definition.
Remainder of Child Constructor (the one invoked by "this")

2) Method calls to Non-final instance methods are mapped at run-time, depending on the instance to which it is pointing.
When the method is overriden in subclass(showDevice in this case), overriden method is called(Dynamic look-up behaviour)

3) When you create instance of Mobile in line (1)

a) With out initializing device reference in Mobile class call is forwarded to constructor of Phone class(refer point 1).
b) Now when showDevice() is called from Phone's constructor method in line (2) is called(Refer point 2).
c) In the call to showDevice() in Mobile's constructor, once again method in line (2) is called.
d) Call to showDevice() in line (3) calls method in line (2).

In line (0) implicitly instance of Mobile class is created and sent to superclass Phone 

Let me if their is any mistake. */ 