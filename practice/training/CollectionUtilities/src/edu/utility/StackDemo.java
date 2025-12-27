package edu.utility;

import java.util.Stack;

public class StackDemo {
	public static void showPush(Stack stack, int a) {
		stack.push(new Integer(a));
		System.out.println("pushed value==>" + a);
		System.out.println("stack==>" + stack);
	}

	public static void showPeek(Stack stack) {
		Object object = (Integer) stack.peek();
		System.out.println("peek value==>" + object);
		System.out.println("stack==>" + stack);
	}

	public static void showPop(Stack stack) {
		Object object = (Integer) stack.pop();
		System.out.println("popped value==>" + object);
		System.out.println("stack==>" + stack);
	}
}
