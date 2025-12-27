package edu;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ReverseLinkedListDemo {
	public static void main(String args[]) {
		List<String> strings = new LinkedList<String>();
		strings.add("anand");
		strings.add("ramu");
		strings.add("raju");
		strings.add("krishna");
		ListIterator<String> li = strings.listIterator();
		while (li.hasPrevious()) {
			System.out.println(li.previous());
		}

	}
}
