public class FinalClassEx 
{
	public static void main(String[] args) 
	{
		System.out.println("Main Class");
		B a = new B();
		C c = new C();
		D d = new D();
		System.out.println("With C obj reference----->"+c.test());
		System.out.println("With D obj reference----->"+d.test());
		System.out.println("get Name value is -----"+a.getName());
	}
	void test()
	{
		System.out.println("this is main test method() ----->");
		
	}
}

/* class FinalClassEx extends B
{
	// Final class can not be sub classed
} */

/*
  class B extends String
  {
  	//can not make String Class as subclass of Class B, b'coz String is a final class
  }
 */
final class B 
{
	public String getName()
	{
		String name=new String("Banu Prakash");
		name.toString();
		return name;
	}
	
	//the methods under the String, String is a subclass of Object superclass
	public String toString()
	{
		return "banu";
	}
	
	public char charAt()
	{
		return 'a';
	}
}

class C
{
	final int c=4;
	public final int test()
	{
		System.out.println("Class C");
		return 1;
	}
	
}

/*class C
{
	public int test()
	{
		System.out.println("Class C");
		return 1;
	}
	
}*/

class D extends C
{
	
/*	public int test()    // can not override the final method in class C 
	{
		System.out.println("Overrided method ----->");
		return 2;
	}
}*/
	public void testMethod()
	{
		System.out.println("value of c ----->"+c);
		//c=5; //can not re assign the final variables
	}
}