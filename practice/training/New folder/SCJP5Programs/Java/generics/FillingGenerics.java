package generics;

import java.util.*;
    class Belt {
    	int size;
    	Belt(int s) { size = s; }
    	static Comparator<Belt> makeComparator() {
	    return new Comparator<Belt>() {
    		public int compare(Belt b1, Belt b2) { 
                    return b2.size - b1.size; }
    	    };
	}
}

public class FillingGenerics {
    public static void main(String[] args) {
	List<Belt> bList = new ArrayList<Belt>();
	bList.add(new Belt(38)); bList.add(new Belt(30));
	bList.add(new Belt(25)); bList.add(new Belt(42));
	Comparator<Belt> comp = Belt.makeComparator();
	Collections.sort(bList, comp);
	for ( Belt b : bList )
	    System.out.print( b.size + " ");
	}
}


/*
import java.util.*;
    class Belt {
    	int size;
    	Belt(int s) { size = s; }
    	static Comparator________ makeComparator() {
	    return new ________ ________() {
    		public int ________ (Belt b1, Belt b2) { return _________ - ________; }
    	    };
	}
}

public class FillingGenerics {
    public static void main(String[] args) {
	List________ bList = new ArrayList ________ ();
	bList.add(new Belt(38)); bList.add(new Belt(30));
	bList.add(new Belt(25)); bList.add(new Belt(42));
	Comparator ________ comp = ________.makeComparator();
	Collections.________( ________, ________ );
	for ( ________ b : ________ )
	    System.out.print( ________ + " ");
	}
}

Fill in the blanks in the above code so that it compiles without errors and warnings, and prints "42 38 30 25 ".
Only the following code fragments can be used, multiple times if necessary, to fill in the blanks.
      	<Belt> <CraftingBelts> <Comparator>
	Belt CraftingBelts Comparator
	compare compareTo sort sortList
	b1 b2 b bList
	b1.size b2.size b.size bList.size() bList.values()
*/