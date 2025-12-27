package edu.test;

import java.util.HashSet;
import java.util.Set;

public class FindDup {
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < args.length; i++) {
			if (!set.add(Integer.parseInt(args[i]))) {
				System.out.println(args[i]);
			}
		}
		System.out.println("\n" + set.size() + "distinct words detected" + set);
	}
}