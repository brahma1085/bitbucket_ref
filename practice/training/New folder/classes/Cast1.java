package classes;

 class Dog { }
 class Beagle extends Dog { }

 class Cast1 {
       public static void main(String [] arfs) {
          	Beagle b1 = new Beagle();
	Dog dog1 = new Dog();
	Dog dog2 = b1;

	b1 = (Beagle) dog1; //compile ok, runtime exception
	b1 = (Beagle) dog2;  // ok
	//b1 = dog2;  // compile-error, type-mismatch
       }
}