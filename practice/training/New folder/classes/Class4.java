class Class4<T> {
    //class Class4 { }   // Compile-time err: name repetition
    public class inClass1 { }

    class Inner2<S, E extends S> {}   // ok
    class Inner3<S, T extends S> {}   // ok

    Class4() {}  // ok

    final int i=4;
    int k=10;

    class Inner4 {
        int j = i;     //ok
        {
              j = k;    //ok
        }
        int n = k; //ok
        Inner4() {
             System.out.println("inner4: j=" + j);  //output is 4
       }
    }

    void foo() {
        class Local {  // a local class
               int m=k;
        }
    }

    public static void main(String[] args) { 
        //Class4<int> outer = new Class4();   // compile-time err: int
        //Class4<Integer> outer = new Class4();  // unchecked warning for Class4()
        Class4<Integer> outer = new Class4<Integer>(); 
        Class4<Integer>.Inner4 inner4 = outer.new Inner4();  //ok
        
  } 
} 