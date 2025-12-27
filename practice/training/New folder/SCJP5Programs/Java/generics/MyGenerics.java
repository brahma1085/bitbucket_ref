package generics;

import java.util.*;

class RentalGeneric<T> { // "T" is for the type parameter
      private List<T> rentalPool; // Use the class type for the List type
      private int maxNum;

      public RentalGeneric(
	int maxNum, List<T> rentalPool) { // constructor takes a List of the class type
	this.maxNum = maxNum;
	this.rentalPool = rentalPool;
      }

      public T getRental() { // we rent out a T blocks until there's something available
	return rentalPool.get(0);
      }

      public void returnRental(T returnedThing) { // and the renter returns a T
	rentalPool.add(returnedThing);
      }
}

class Car {}

class Cat {
       private String name;
       public Cat(String name) { this.name = name; }
}

public class MyGenerics {
      public static void main (String[] args) {
      	//make some Cars for the pool
	Car c1 = new Car();
	Car c2 = new Car();
	List<Car> carList = new ArrayList<Car>();
	carList.add(c1);
	carList.add(c2);
	RentalGeneric<Car> carRental = new RentalGeneric<Car>(2, carList);
	// now get a car out, and it won't need a cast
	Car carToRent = carRental.getRental();
	carRental.returnRental(carToRent);
	// can we stick something else in the original carList?
	//carList.add(new Cat("Fluffy"));    // compile-time err: cannot find symbol
                  // : method add(Cat)                     location: interface java.util.List<Car>
      }
}