package OverrideMethods;

public class OverEx 
{
	public static void main(String[] args) 
	{
		A a = new A();
		B b = new B();
		A a1 = new B();
		// B b1 = new A(); //Subclass obj reference can not point to the Super class object
		a.test(); //Super class ----> A
		b.test(); //Sub Class ----> B
		a1.test(); //Sub class ---> B
		// a1.eat();
	}
}
class A
{
	public static void test()
	{
		System.out.println("Super class ----> A");
	}
}
class B extends A
{
/*	private void test() // we can not give less access to the overridded method
	{
		System.out.println("Sub Class ----> B");  
	}
*/	
	public static void test()
	{
		System.out.println("Sub Class ----> B");
	}
	public void eat()
	{
		System.out.println("The eat method ---- >");
	}
}