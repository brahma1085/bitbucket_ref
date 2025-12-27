package generics;

import java.util.*;

class  Generics6<T>{ 
    class Inner1<U> {                 // ok   (b) Non-static member classes can have type parameters. 
          T t;                        // ok
          T copy() {return t; };      //ok
    }

    class inner2 {
          T t;                        // ok
          // static T t1=1;           // compile-time err
          T copy() {return t; };      //ok
    }

    interface itf1<U>{     // ok   (a) interface with type parameter
          //T t=(T)10;     // compile-time err
          //T copyT();      // compile-time err, cannot use the type parameter of the outer class
          U copy(); 
    }

    //enum State<U> {      // compile-time error    (f) Enum type cannot have type parameters.
    enum State {
          VALID, INVALID;
          //T t;           //compile-time error
          //T state();     //compile-time error
    }

    static {
	  //T t1;          //compile-time error
    }

    //static T t2;         //compile-time error

    static class NestedClass2<U> {  //ok   (a) static memeber class with type parameter
          U u;  
          //T t3;          //compile-time error, cannot use the type parameter of the outer class
     }

    static <U> void staticMethod() {  //ok  //static member method with type parameter 
          U u;  
    }

    //class MyException<T> extends Exception { }               // (d) Exception cannot have type parameter
    //<U> void testExceptionTypeBound(U extends Exception) {}  // (e) Exception types cannot be used as type parameter bounds.

    public static void main(String[] args) { 
        //Anonymous class
        itf1<Integer> g = new itf1<Integer>() { public Integer copy() { return 3; }  };   //ok

        // (g) Parameterized types ArrayList<String> and ArrayList<Long> have different types at compile time, 
        // but share the same runtime type, i.e. ArrayList
        ArrayList<String> listS = new ArrayList<String>();
        ArrayList<Long> listL = new ArrayList<Long>();
        //listS = listL;                                      // Compiler error: they are different types.
        System.out.println(listS instanceof ArrayList);       //true  
        System.out.println(listL instanceof ArrayList);       //true

        //(h) Arrays of concrete parameterized types cannot be created
        //ArrayList<String>[] arrConcrete = new ArrayList<String>[2]; // compiler error

        //(i) Arrays of unbounded wildcard parameterized types can be created, i.e. new ArrayList<?>[2].
        ArrayList<?>[] arrUnbounded = new ArrayList<?>[2];            // ok

        //(j) Arrays of raw types can be created, i.e. new ArrayList[2].
        ArrayList[] arrRaw = new ArrayList[2];                        // ok
  } 
} 

/* 
Which statements are true about generics in Java? Select the 2 correct answers.
(a) Static member classes and interfaces can have type parameters.    // True, but they cannot use the generic parameters of their outer class.
(b) Non-static member classes can have type parameters.               // True
(c) Anonymous classes cannot have type parameters.                    // No
(d) Exception types can have type parameters.                         // No
(e) Exception types can be used as type parameter bounds.             // No
(f) Enum type can have type parameters.                               // No
(g) Parameterized types ArrayList<String> and ArrayList<Long> have different types at compile time, but
share the same runtime type, i.e. ArrayList.                          // True
(h) Arrays of concrete parameterized types cannot be created, i.e. new ArrayList<String>[2].   // True
(i) Arrays of unbounded wildcard parameterized types can be created, i.e. new ArrayList<?>[2]. // True
(j) Arrays of raw types can be created, i.e. new ArrayList[2].        // True
*/
