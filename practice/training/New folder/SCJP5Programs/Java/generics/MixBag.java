package generics;

class MixBag<T extends Comparable<Integer> & Comparable<String>> {// compiler error:Comparable<Integer> Comparable<String>
	public static void do1() {T t; }             // compiler error: static environment cannot use non-static T
	public static <T> void do2() {T t; }             
	public int compareTo(Integer f) { return 0; }
	public int compareTo(String f) { return 0; }
	public <T extends Exception> void handleException(T t) throws T {
		try { throw t; }
		catch (T e) { }   // compiler error
	     }
}

/*
The compile flags errors at which lines in the above code? Select the 3 correct answer.
(a) At (1)       //true
(b) At (2)       //true
(c) At (3)
(d) At (4)
(e) At (5)
(f) At (6)
(g) At (7)
(h) At (8)      //true
*/