package classes;

class FinalTest1{
   public static final int i = 5; // no static here
   public static void main(String[] args)  {
        FinalTest1 c = new FinalTest1();
        int j = 3;
        switch (j)    {
             case i : System.out.println("5"); 
                             break; // should work, no?
             default : System.out.println("not 5"); 
                            break;
       }
  }
}