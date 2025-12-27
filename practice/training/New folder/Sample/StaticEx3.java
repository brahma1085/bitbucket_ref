
class Parent {
         static String shiny() { return "1"; }
}

public class StaticEx3 extends Parent 
	{
        public static void main(String [] args) 
        {
	        String str = shiny() + getShiny();   
	        System.out.println(str); //11
	        StaticEx3 sm = new StaticEx3();
	        sm.instanceMethod(str);    
        } 
         static String getShiny() { return shiny(); 
     }
     void instanceMethod(String s) 
     {
         s = s+super.shiny(); 
         System.out.println(s);
     }
}