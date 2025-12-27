package edu;

public class BreakLoop {
	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			System.out.print("Pass " + i + ": ");
			for (int j = 0; j < 10; j++) {
				if (j == 9 || j==6 || j==8)
					break;
				System.out.print(j + " ");
			}
			System.out.println();
		}
		System.out.println("Loops Complete");
	}
}
