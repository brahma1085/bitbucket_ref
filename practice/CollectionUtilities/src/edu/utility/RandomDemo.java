package edu.utility;

import java.util.Random;

public class RandomDemo {
	double value;

	public void getRandomNumbers() {
		Random random = new Random();
		double sum = 0;
		int bell[] = new int[10];
		for (int i = 0; i < 10; i++) {
			value = random.nextGaussian();
			System.out.println("gaussian value==>" + value);
			sum += value;
			double t = -2;
			for (int x = 0; x < 10; x++, t += 0.5) {
				if (value < t) {
					bell[x]++;
					break;
				}
			}
			System.out.println("sum of values==>" + sum);
			System.out.println("average of values==>" + sum / 100);
			System.out.println("------------------------------------");
		}
		System.out.println("generating bell curve");
		for (int i = 0; i < 10; i++) {
			for (int x = bell[i]; x > 0; x--)
				System.out.print("*");
			System.out.println();
		}
	}
}
