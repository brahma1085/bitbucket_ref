import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LinkedListTest {
	public static void main(String[] args) {
		List<String> str = new LinkedList<String>();
		str.add("anand");
		str.add("ramu");
		str.add("raju");
		str.add("krishna");
		System.out.println(str);
		ListIterator<String> listIterator = str.listIterator();
		while (listIterator.hasNext())
			System.out.println(listIterator.next());
		System.out.println("----------------------------");
		while (listIterator.hasPrevious())
			System.out.println(listIterator.previous());
		System.out.println("----------------------------");
	}
}
