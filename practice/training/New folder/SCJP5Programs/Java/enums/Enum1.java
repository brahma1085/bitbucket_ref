package enums;

public enum Enum1 {
    MERCURY (3.303e+23, 2.4397e6),
    VENUS (4.869e+24, 6.0518e6);

     private final double mass; // in kilograms
     private final double radius; // in meters

    //MERCURY (3.303e+23, 2.4397e6),    // error to enum constants here
    //VENUS (4.869e+24, 6.0518e6);

    Enum1(double mass, double radius) {
         this.mass = mass;
         this.radius = radius;
   }
}

/*enum constants must be declared before any other declarations in an enum type
*/