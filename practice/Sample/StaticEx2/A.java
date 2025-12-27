package StaticEx2;


class B
{
	int p;
	int test()
	{
		System.out.println("test() method in class B");
		return p;
	}
	int testing1()
	{
		System.out.println("testing1() method in B class");
		return 0;
	}
}
class D extends B
{
	int k = 4;
	int test()
	{
		System.out.println("test() method in class D");
		return k;
	}
	int testing()
	{
		System.out.println("testing() method in class D");
		return 5;
	}
}
public class A 
{
	public static void main(String[] args) 
	{
		D d = new D();
		B b = new B();
		//D d1 = new B(); //Super class object can not point to subclass object
		B b1 = new D();
		System.out.println("value of test()-- D d = new D()  ---->"+d.test());
		System.out.println("value of test()-- B d = new B()  ---->"+b.test());
		System.out.println("value of test()-- B d = new D()  ---->"+b1.test());
		//System.out.println("value of test()-- B d = new D()  ---->"+b1.testing());
		System.out.println("value of test()-- B d = new D()  ---->"+b1.testing1());
	}
}
