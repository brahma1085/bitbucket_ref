public class Assertion1 { 
   public void methodA(int i)  { 
      assert i >= 0 : methodB();
      System.out.println(i); 
  } 

  public String methodB() { 
     System.out.println("jfgjk");
     return "The value must not be negative"; 
  } 

  public static void main(String args[]) { 
     Assertion1 test = new Assertion1(); 
     test.methodA(-10); 
  } 
} 


/* java -ea AssertionTest 
   This will enable assertions and result in an assertion error in your program. 
    Assertions are available from java version 1.4 and above.
*/