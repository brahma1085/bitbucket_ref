 package array;

 class Animal{    }
 class Dog extends Animal{    }

 public class ArrayTest {
    public static void main(String[] args)     {
       Animal[] a=new Animal[]{ new Dog(),new Dog() };        
       Dog[] d=(Dog[])a;     // run-time ClassCastException

       Animal[] a=new Dog[]{ new Dog(),new Dog() };        
       Dog[] d=(Dog[])a;     //ok
    } 
}