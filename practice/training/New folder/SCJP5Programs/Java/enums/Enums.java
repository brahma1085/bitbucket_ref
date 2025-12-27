package enums;

import enums.test.Enum1;
import static enums.test.Enum2.*;  //should use *

enum Enum3 { 
       MAX { public int returnIt() {return 10; }; },
       MIN { public int returnIt() {return 1;}; };
       		
       public abstract int returnIt();		
}

class Enums {
       public static void main(String[] args) {
           Enum3 emax = Enum3.MAX;
           Enum3 emin = Enum3.MIN;
           emax.returnIt();
       }
}

