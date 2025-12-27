import java.lang.String;
import java.util.*;

class X< T extends List<? extends String >> { 
   public void someMethod(T t) { 
      //t.add("12"));
      //t.add("34");
      String n = t.remove(0);  
  } 
} 

class  Generics4{ 
  public static void main(String[] args) { 
     X<ArrayList< String >> x1 = new X<ArrayList<String>>();    
     ArrayList< String >  list = new ArrayList<String > ();
     x1.someMethod(list);
     System.out.println(list);
  } 
} 
