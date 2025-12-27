package wrapper;

import java.util.*;

public class Wrappers {
      private Map accountTotals = new HashMap();
      private int retirementFund;

      public static void main(String[] args) {
           Double d = new Double("23.0f");
           Double d1 = new Double("23.0");
           Float f = new Float("23.0");
            //Long l = new Long("23l");   // run-time: NumberFormatException
           Long l = new Long("23");
           //System.out.println("d="+d);

          System.out.println("new Boolean(\"anyString\") is " +  new Boolean("anyString"));  //false
          System.out.println("new Boolean(null) is " +  new Boolean(null));   //false
          //System.out.println("new Boolean(40) is " +  new Boolean(40));   //compile err
          System.out.println("new Boolean(\"true\") is " +  new Boolean("true"));   //true
          System.out.println("new Boolean(true) is " +  new Boolean(true));   //true
          System.out.println("new Boolean(\"false\") is " +  new Boolean("false"));   //false
          System.out.println("new Boolean(false) is " +  new Boolean(false));   //false

          testAutobox(10);
          testAutounbox(new Integer(20));

          Wrappers w = new Wrappers();
          w.setBalance("Alice",  190);
          w.setBalance("Bert",  300);
          w.getBalance("Alice");
       }

      static void testAutobox(Integer i) {
              System.out.println(i);
      }
      static void testAutounbox(int i) {
              System.out.println(i);
      }

     public int getBalance(String accountName) {
          	Integer total = (Integer) accountTotals.get(accountName);
          	if (total == null)
	total = Integer.valueOf(0);
	return total.intValue();
      }

      public void setBalance(String accountName, int amount) {
	//accountTotals.put(accountName, Integer.valueOf(amount)); 
                 //accountTotals.put(accountName, amount.intValue());  //error
                 accountTotals.put(accountName, amount); 
      }
}