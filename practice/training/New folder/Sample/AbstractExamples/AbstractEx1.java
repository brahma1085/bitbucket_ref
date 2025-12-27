package AbstractExamples;

public abstract class AbstractEx1 
{
	public static void main(String args[])
	{
		System.out.println("This is abstract class------>");
	}
	public abstract void test(); //abstract method
	public void test1()
	{
		System.out.println("This is non abstract class----->");
	}
}

abstract class subClass extends AbstractEx1
{
	public void test()
	{
		System.out.println("this is abstract method implementation---->");
	}
}

class subSubClass extends subClass
{
	//subClass sub = new subClass(); can not create an object refrence of an abstract class
	public subSubClass() {
		// TODO Auto-generated constructor stub
	}
	subSubClass subSub = new subSubClass();
	public void test(){
		System.out.println("hi from subsubclass");
	}
}

