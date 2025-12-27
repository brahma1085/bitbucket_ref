package classes;

class Outer {
    static class Nested1 {}
}

public class NestedClass {
    static class Nested2 {}
  
    public static void main(String[] args) {
          Nested2 in1 = new NestedClass.Nested2();
          Nested2 in2 = new Nested2();

          //Outer.Nested1 in3 = Outer.Nested1();  //compile-err
          Outer.Nested1 in4 = new Outer.Nested1();
    }
}

