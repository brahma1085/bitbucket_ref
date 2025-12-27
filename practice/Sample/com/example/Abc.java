package com.example;
abstract class AbstractEX
{
	int a;
	int y;
	String name;
	public abstract int testAbs();
}
class Abc extends AbstractEX
{
	public static void main(String args[])
	{
		Abc abEx = new Abc();
		System.out.println("the method result is-----"+abEx.testAbs());
	}
	public int testAbs()
	{
		return 4;
	}
}