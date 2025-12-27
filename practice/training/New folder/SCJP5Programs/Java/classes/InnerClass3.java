package classes;

public class InnerClass3 { 

    private double d1=1.0; 

    private class Inner1{ 
     	public double methoda(){return d1;} }


    //Compiler-errors:
    //1.cannot access instance variable d1 from static context
    //2. inner class cannot have static methods.
    /*protected class Inner2{ 
        static double methoda(){return d1;} 
    }  
    */

    public abstract class Inner3{ 
        public abstract double methoda(); }   //ok

    //Test subclassing this outer class
    class SubClassA extends InnerClass3{} 
    class SubClassB extends InnerClass3{} 
    public void testSubClass(SubClassA foo){ 
        InnerClass3 bar=foo; }

    public void getIt(final int input) { 
       class LocalInner { 
            public int getIt() { 
               return input;   // input should be final
            } 
      } 
    } 

    public static void main(String args[]) { 
        InnerClass3 out = new InnerClass3();
        out.testSubClass(out.new SubClassA());  //ok
   } 

}

/*The inner class LocalInner cannot access the local variable "input" because it is not final.
*/