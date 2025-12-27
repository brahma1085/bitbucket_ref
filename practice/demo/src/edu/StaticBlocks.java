package edu;

public class StaticBlocks {
	int a;
	static int b;
	static {
		System.out.println("static block1");
	}
	{
		System.out.println("non static block");
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		StaticBlocks blocks = new StaticBlocks();
		StaticBlocks blocks2 = null;
		System.out.println(blocks.a + " , " + blocks.b + " , " + b + " , "
				+ blocks2.b );
		Class class1 = Class.forName("edu.StaticBlocks");
		class1.newInstance();
	}
}
