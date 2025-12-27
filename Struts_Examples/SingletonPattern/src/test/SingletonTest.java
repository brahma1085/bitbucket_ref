package test;

import edu.Singleton;

public class SingletonTest {
	public static void main(String[] args) {
		Singleton singleton = Singleton.getInstance();
		String string = singleton.getString();
		System.out.println(string);
		System.out.println("address of the object==>" + singleton);
	}
}
