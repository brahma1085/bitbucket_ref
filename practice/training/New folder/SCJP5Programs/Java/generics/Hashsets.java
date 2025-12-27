package generics;

import java.util.*;

public class Hashsets extends HashSet<Person> {
         public static void main(String[] args) {
	Hashsets g = new Hashsets();
	g.add(new Person("Hans"));
	g.add(new Person("Lotte"));
	g.add(new Person("Jane"));
	g.add(new Person("Hans"));
	g.add(new Person("Jane"));
	System.out.println("Total: " + g.size());
        }

        public boolean add(Person o) {
	System.out.println("Adding: " + o);
                 return super.add(o); 
        }

        /*
        public boolean add(Object o) {
	System.out.println("Adding: " + o);
	//return super.add(o);  // compile-time error: cannot find symbol
                 return super.add(o);    // compile-time error: add(Object), add(Person)
        }*/
}

class Person {
        private final String name;
        public Person(String name) { this.name = name; }
        public String toString() { return name; }
}