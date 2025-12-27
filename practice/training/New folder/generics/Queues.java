package generics;

import java.util.*;

class Queues {
        public static void main(String[] args) {
        	Queue<String> q = new LinkedList<String>();
        	q.add("Veronica");
        	q.add("Wallace");
        	q.add("Duncan");
        	showAll(q);
        }

        public static void showAll(Queue q) {
	q.add(new Integer(42));   //compile-time warning
	while (!q.isEmpty())
	        System.out.print(q.remove() + " ");
	}
}

//ok