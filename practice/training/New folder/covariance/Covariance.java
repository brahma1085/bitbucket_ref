package covariance;

class A {  int x = 5;}
class B extends A {  int x = 6; } 
class C extends B {}

class X { 
     public B getIt() {return new B();} }

class Y extends X {
     // (1) Insert code here.

     //public C getIt() { return new C(); }   //ok
     //public B getIt() { return new C(); }   //ok
     //public A getIt() { return new C(); }   //compiler err
     //public A getIt() { return new B(); }   //compiler err
     //public C getIt() { return new B(); }   //compiler err
}


class Sub extends Covariance {
	public B getObject() {
		System.out.println("In B");
		return new B();
      	}
} 

public class Covariance {
	public A getObject() {
		System.out.println("In A");
		return new A();
		}		

	public static void main(String[] args) {
 		Covariance c = new Sub();
 		System.out.println(c.getObject().x);

                System.out.println(c.getObject().getClass().getSimpleName());
                B b = (B)c.getObject();  
                System.out.println(b.x);
 	} 			
}

