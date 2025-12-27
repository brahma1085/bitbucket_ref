package edu;

public class MCZ11 {
	public static void main(String[] args) {
		System.out.print(Byte.MIN_VALUE + ",");
		System.out.println(Byte.MAX_VALUE);
		System.out.print(Short.MIN_VALUE + ",");
		System.out.println(Short.MAX_VALUE);
		System.out.print(Integer.MIN_VALUE + ",");
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.toBinaryString(Byte.MAX_VALUE));
		System.out.println(Integer.toHexString(Byte.MAX_VALUE));
		System.out.println(Integer.toOctalString(Byte.MAX_VALUE));
		System.out.println(Integer.toString(Byte.MAX_VALUE));
		System.out.println(Long.toHexString(Byte.MAX_VALUE));
		System.out.println(Long.toHexString(Character.MAX_VALUE));
		System.out.println(Long.toHexString(Short.MAX_VALUE));
		System.out.println(Integer.toHexString(Integer.MIN_VALUE));
		System.out.println(Integer.toHexString(Integer.MAX_VALUE));
	}
}