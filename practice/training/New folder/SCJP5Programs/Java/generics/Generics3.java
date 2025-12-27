import java.util.*;

//class X< T extends List<? extends Number> > { 
class X< T extends List<S>, S  extends Number > { 
   public void someMethod(T t, S s) { 
    //t.add(new Long(0L));    // error 
    t.add(s);
    //Number n = t.remove(0);   //ok
  } 
} 

class  Generics3{ 
  public static void main(String[] args) { 
     X<ArrayList< Long >, Long>   x1 = new X<ArrayList<Long>, Long>();  
     //X<ArrayList< String >> x2 = new X<ArrayList<String>>();    // error 
     ArrayList< Long >  list = new ArrayList< Long > (5);
     Long l = 10L;
     x1.someMethod(list, l);
     System.out.println(list);
  } 
} 
