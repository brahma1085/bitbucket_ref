class Animal {
    void method() throws Exception {}
    Animal( String name ) {
           System.out.println(name);
    }
}

class dog extends Animal {     
     void method() throws Exception {}
}

class Class3  {
      public static void main (String [] args ) {
             new Animal("Giraffe");
      }
}

/* compile-time err:   Animal has no default constructor, but dog needs one. */