package edu.reflect;

public class HelloWorldRefl {

	public static void main(String[] args) {
		if (args.length > 0) {
			System.out.println(args[0]);
		} else {
			System.out.println("pass command line arguments==>");
		}
	}
}