package edu.test;

public class Arraydeclaration {

	public static void main(String[] args) {
		String names[] = new String[5];
		for (int x = 0; x < args.length; x++)
			names[x] = args[x];
		System.out.println(names[2]);
	}
}
