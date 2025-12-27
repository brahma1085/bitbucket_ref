
package staticExamples;

class A
{
	//can not declare static for a class 
	//ie static class A
	//it can have only public/default or abstract or final
}
class B 
{
	static int i = 0;
	static String name; //it takes the default value as null
	static int v;
	int c;
	B()
	{
		// static int k; // only static members can be accessed by static methods
		v+=1;
 	}
/*	B()
	{
		c+=1; // access of non - static method in a static method
	}
	
}*/
}
public class StaticEx 
{
	int abc;
	static int m;
	public static void main(String args[]) 
	{
		System.out.println("the value of i in class B is -----"+B.i);
		B.i = 20;
		System.out.println("the value of i is ---->"+B.i);
		System.out.println("Value of name is ----->"+B.name); // the statioc values takes the default values
		// we can initialize it to any value say
		B.name ="banu";
		System.out.println("the value of name is now=----- >"+B.name);
		B b = new B();
		System.out.println("value of v is ----- > "+B.v);
		new B();
		System.out.println("value of v is ----- > "+b.v);
		new B();
		new B();
		System.out.println("value of v is ------- >"+b.v);
		//b.v value is accessed because they are declared as static
		//now try to access the non - static varaible in a static method
		// System.out.println(""+B.c); // compilation error due to access of non static meember in a static method
		
	//	System.out.println("trying to access non - static meember in the main class--->"+abc); //compilation error
		
		System.out.println("trying to access static meember in the main class--->"+m);//executes fine
	}
}
