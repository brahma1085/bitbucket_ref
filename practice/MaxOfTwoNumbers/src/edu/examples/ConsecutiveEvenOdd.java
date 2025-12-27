package edu.examples;

import java.util.Scanner;

public class ConsecutiveEvenOdd {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int coneven = 0, conodd = 0, sumeven = 0, sumodd = 0;
		while (a > 0) {
			if (a % 2 == 0) {
				coneven++;
				sumeven = sumeven + a;
			} else {
				conodd++;
				sumodd = sumodd + a;
			}
			a--;
		}
		int evenavg, oddavg;
		evenavg = sumeven / coneven;
		oddavg = sumodd / conodd;
		System.out.println(evenavg);
		System.out.println(oddavg);
	}
}
