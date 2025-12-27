package classes;

class A{
    static int i;
    A(){ ++i; }
    private int get(){ return ++i; }
    static final void method1(){}  //Cannot be overriding
    static void method2(){}
}
 
class B extends A{
    B(){ i++; }
    int get(){ return ( i + 3); }
    static void method2(){}  //ok, shadowing
}
 
class Overriding7 extends B{
    public static void main(String ka[]){
        Overriding7 obj = new Overriding7();
        A ob_a = new A();
        ob_a = (A)obj;
        //System.out.println(ob_a.get());  //compiler error: get() is private in A
    }
}
