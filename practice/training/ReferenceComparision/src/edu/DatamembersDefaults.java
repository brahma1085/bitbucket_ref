package edu;

class Alpha {
	byte x1;
	int x2;
	short x3;
	long x4;
	double x5;
	float x6;
	boolean x7;
	char x8;
	Alpha x9;
	String x10;
}

public class DatamembersDefaults {
	public static void main(String[] args) {
		Alpha alpha = new Alpha();
		System.out.println(alpha.x1 + "|" + alpha.x2 + "|" + alpha.x3 + "|"
				+ alpha.x4 + "|" + alpha.x5 + "|" + alpha.x6 + "|" + alpha.x7
				+ "|" + alpha.x8 + "|" + alpha.x9 + "|" + alpha.x10);
	}
}
