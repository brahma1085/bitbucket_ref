package edu.test;

import java.util.Stack;

import edu.utility.StackDemo;

public class StackDempTest {

	public static void main(String[] args) {
		Stack stack = new Stack();
		StackDemo.showPush(stack, 12345);
		StackDemo.showPeek(stack);
		StackDemo.showPop(stack);
	}
}