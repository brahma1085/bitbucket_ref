package generics;

class GenericsCasting<T> {
     private T data;
     GenericsCasting(T d) { data = d; }
     
     public boolean equals(Object other) {
	if (this == other) return true;
	if (other == null) return false;
	if (this.getClass() != other.getClass()) return false;

	// (1) Insert code here.
	//GenericsCasting<?> otherItem = (GenericsCasting<?>) other;     //ok
	//GenericsCasting<T> otherItem = (GenericsCasting<T>) other;     //warning
	//GenericsCasting<T> otherItem = (GenericsCasting<?>) other;     //error
	//GenericsCasting<?> otherItem = (GenericsCasting<T>) other;     //warning

	return (this.data.equals(otherItem.data));
     }
}

/*
Which method declaration when inserted at (1) will result in the above code to compile without errors and warnings?
Select the one correct answer.
(a) Item<?> otherItem = (Item<?>) other;
(b) Item<T> otherItem = (Item<T>) other;
(c) Item<T> otherItem = (Item<?>) other;
(d) Item<?> otherItem = (Item<T>) other;
*/