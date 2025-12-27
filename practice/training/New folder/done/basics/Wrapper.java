package basics;

class Wrapper {
       public static void main(String[] args) {
              Boolean b = new Boolean("FasLe");
              //b = new Boolean(FALSE);  // error
              b = new Boolean(false);
 
              Float f2 = Float.valueOf("3.14");
              System.out.println(f2);

             String s = "-";
             Integer x = 343;
             long L343 = 343L;
             if(x.equals(L343)) s += ".e1 ";
             if(x.equals(343)) s += ".e2 ";
             Short s1 = (short)((new Short((short)343)) / (new Short((short)49)));
             if(s1 == 7) s += "=s ";
             if(s1 < new Integer(7+1)) s += "fly ";
             System.out.println(s);

             System.out.println("Integer x:   "+ x);
             System.out.println("long L343:   "+ L343);
             System.out.println("L343.equals(x):   "+ ((Long) L343).equals(x));    //false
             System.out.println("L343.equals(x):   "+ ((Long) L343).equals((long)x));   //true

             int x1 = 343;
             System.out.println("L343.equals(x1):   "+ ((Long) L343).equals(x1));    //false
             System.out.println("L343.equals(343):   "+ ((Long) L343).equals(343));    //false

             short sh1 = 343;
             System.out.println("x.equals(sh1):   "+ x.equals(sh1));    //false

             Integer x2 = 343;
              System.out.println("x.equals(x2):   "+ x.equals(x2));    //true
             
             System.out.println("x.equals(new Integer(343)):   "+ x.equals(new Integer(343)));    //true
             System.out.println("x.equals(new Short(343)):   "+ x.equals(new Short((short)343)));    //false

             Byte byt;
             //byt  = new Byte(10);   // compile-err
             byt = new Byte((byte)10);

             Long l = new Long(10);
             l = new Long((long)10);
      }
}