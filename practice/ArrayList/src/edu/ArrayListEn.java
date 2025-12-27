package edu;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListEn {
	public static void main(String[] args) {
		ArrayList list = new ArrayList();
		list.ensureCapacity(2);
		int[] a = { 12, 23, 334, 43, 34 };
		int[] a2 = { 12, 23, 334, 43, 45 };
		System.out.println(Arrays.binarySearch(a, 12));
		System.out.println(Arrays.equals(a, a2));
		Arrays.fill(a2, 2, 4, 2);
		Arrays.sort(a2, 1, 4);
		for (int i : a2)
			System.out.println(i);
	}
}
