package classes;

class Parent {
         static String shiny() { return "1"; }
}

class StaticMethod1 extends Parent {
         public static void main(String [] args) {
	String str = shiny() + getShiny();   // str = "11"
	//str = str + super.shiny();    // 无法从静态上下文中引用非静态 变量 super
	System.out.println(str);
                 StaticMethod1 sm = new StaticMethod1();
                 sm.instanceMethod(str);    // ok
        } 

         static String getShiny() { return shiny(); }
         void instanceMethod(String s) {
                  s = s+super.shiny(); 
	System.out.println(s);
         }
}