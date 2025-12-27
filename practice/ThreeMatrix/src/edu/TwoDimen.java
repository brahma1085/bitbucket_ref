package edu;


public class TwoDimen {
	public static void main(String[] args) {
		int d2[][] = new int[4][5];
		int i, j, k = 0;
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 5; j++) {
				d2[i][j] = k;
				k++;
				System.out.print(d2[i][j] + " ");
			}
			System.out.println();
		}
	}
}
