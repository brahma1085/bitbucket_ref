package classes;

class Constructor2 {
         Constructor2() {}
         Constructor2(int i) { 
               this(); 
               //Constructor2();  // error
         }
         void method1() {
              //Constructor2();   // error
               //this();   // error
         }
}