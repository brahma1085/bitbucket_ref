package generics;

import java.util.*;

abstract class Animal {
         public abstract void checkup();
}

class Dog extends Animal {
         public void checkup() { // implement Dog-specific code
	System.out.println("Dog checkup");
         }
}

class Cat extends Animal {
         public void checkup() { // implement Cat-specific code
	System.out.println("Cat checkup");
         }
}

class Bird extends Animal {
         public void checkup() { // implement Bird-specific code
	System.out.println("Bird checkup");
         }
}

class PolymorphismGenerics {
         public static void main(String[] args) {
               PolymorphismGenerics pg = new PolymorphismGenerics();
               pg.foo();
         }

         public void foo() {
	Cat[] cats = {new Cat(), new Cat()};
	addAnimal(cats); // no problem, send the Cat[] to the method
        }

        public void addAnimal(Animal[] animals) {
	animals[0] = new Dog(); // Eeek! We just put a Dog in a Cat array!
        }
}