class Animal {
 
    Animal( ) {
           System.out.println(name);
    }
}

class dog extends Animal {     
  
  Animal(){
            System.out.println("am in dog..");
         }
    
}

class Class3  {
      public static void main (String [] args ) {
            Animal a =  new Animal();
      }
}

/* compile-time err:   Animal has no default constructor, but dog needs one.  // void method() throws Exception {} */