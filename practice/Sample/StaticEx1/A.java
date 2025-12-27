package StaticEx1;

public class A {
	
	/*A()
	{
		
	}*/
	public int getName()
	{
		System.out.println("name is ---------->");
		return 4;
	}
	int k;
	A(int a)
	{
		k=a;
		System.out.println("value of k is -----"+k);
	}
	public static void main(String[] args) 
	{
		A a = new A(4);
		System.out.println("getName() is --------->"+a.getName());
		//Static methods can access static & non - static methods,
		// but static method c
		
	}
}
