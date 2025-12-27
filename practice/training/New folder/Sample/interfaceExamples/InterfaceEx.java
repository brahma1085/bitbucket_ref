package interfaceExamples;

public interface InterfaceEx 
{
	int k=0; // all variables has to be assigned a value and by default it is public static final(ie public static final int k=0;)
	// valid declarations of interface fields
		
	public static final int k1=0;
	static final int k2=0;
	final int k3=0;
	static final public int k4=0; // any combination of public,static and final
	
	String name="banu";
	void test(); // by default it is public & abstract(ie public abstract void test() 
	//static void printName();// can not be declared as static
	//final test1();// can not be declared as final
	//private void test() // interface methods should always be public
	//protected void test() // interface methods should always be public
	
	//valid declarations of interface methods 
	public void test1();
	public abstract void test2();
	abstract void test3();
	abstract public void test4();
}
interface C
{
	// here the interface declared is by default it is abstract
}

/* interface Ex1 extends Ex1
{
	//compile-err: cannot extends itself
}*/

interface Temp extends C
{
	
}
/*interface C extends Temp
{
	//compilation errors
}*/

interface B extends InterfaceEx,C
{
	// an interface can extend 1 or more interfaces
	void test();
}

class Abc
{
	
}
/* interface Abcde extends Abc
{
	//an interface can not extend a class
}*/
/*interface D implement C
{
	// an interface can not implement another interface / class
}*/

/*interface E implements Abc
{
	// an interface can not implement another interface / class
}*/
class Abcd implements InterfaceEx
{
	public void test()
	{
		System.out.println("test --- >");
		System.out.println("k value issss ----->"+k);
		//k=4; k can not be re initialized as it is final
		System.out.println("k can not be re initialized as it is final"+k);
	}
	public void test1()
	{
		System.out.println("test1 --- >");
	}
	public void test2()
	{
		System.out.println("test2 --- >");
	}
	public  void test3()
	 {
		System.out.println("test3 --- >");
	 }
	 public void test4()
	 {
		 System.out.println("test4 --- >");
	 }
}
interface StringEx
{
	// void toString(); //toString() is pre defined funtion
}
class C2
{
	//empty class
}
class C3
{
	//empty class
}
class C1 extends C2
{
	// class can extend only 1 class
}

/* class C4 extends C3,C2
{
	//Class can not extend more than 1 class
}*/
interface G
{
	
}
interface D
{
	
}
interface F extends D,G
{
	// anm interface can extend 1 or more interfaces
}
/*interface H implements D
{
	// an interface can not implement another interface
}*/
/*interface I implements C1
{
	//an interface can not implement a class
}*/

class C5 implements C
{
	
}

class C6 implements C,D
{
	      //an interface can implement more than 1 interface
}

/*class C7 extends D
{
	//a class can not extend an interface
}*/
/*interface E extends C2
{
	// an interface can not extend a class
}*/

/*interface I1 implements C1
{
	//an interface can not implement a class
}*/
class C10 extends C1 implements D
{
	// is possible
}
/*class C1 implements E extends C2
  {
 	// is not possible
  } 
 */