//#import java.

public class Wrapper1 {
	public static void main(String[] args) {
		/*
		Integer I = new Integer(2);
		int i = I;
		i = new Integer(12);
		System.out.println(i);
		*/
		
		System.out.println(String.format("%,6.1f", 42004320.003));
		System.out.println(String.format("%,6.1f", 4.003));
		System.out.println(String.format("%,d", 4.003));
	}
}