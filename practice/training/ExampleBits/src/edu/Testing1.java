package edu;
public class Testing1 {
	public static synchronized void a1() {
	}

	public static void main(String[] args) {
		StringBuffer buffer = new StringBuffer("Amit");
		StringBuffer buffer2 = new StringBuffer("Amit");
		String string = "Amit";
		System.out.println(buffer == buffer2);
		System.out.println(buffer.equals(buffer2));
		System.out.println(buffer.equals(string));
		System.out.println("Poddar".substring(3));
	}
}
