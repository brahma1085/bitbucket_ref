package edu;

class Sample {
	private Sample() {
		System.out.println("how dare to call me");
	}

	public static Sample getSample() {
		Sample sample = new Sample();
		return sample;
	}
}

public class ConstructorsDemo {
	public ConstructorsDemo() {
		System.out.println("from costructor");

	}

	public static void main(String[] args) {
		ConstructorsDemo demo = new ConstructorsDemo();
		System.out.println(Sample.getSample());
	}
}
