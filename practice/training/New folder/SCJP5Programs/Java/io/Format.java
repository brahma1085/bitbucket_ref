package io;


class Format {
     public static void main(String[] args) {
           System.out.printf("%d  %d    ", 10, 34);   
           //System.out.printf("%2$d", 10);    //runtime exception
           System.out.printf("d ", 10, 23.45);   // print out only:  d
           //System.out.println("d ", 10);    // compile-time error
           System.out.println();

           System.out.format(" %s", new Integer("123"));   // output 123
           System.out.format(" %s", 456);   // output 456, boxing and toString()
           System.out.format(" %s", null);   // compiler warning, but output "null"
           System.out.format(" %S", 'c');   // output  'C'
           //System.out.printf("%f", 123);    //runtime exception
           System.out.println();

           System.out.printf("%b %b %b %b %b", true, false, "True", "False", "false");   //true,false,true,true,true 
           System.out.println();

     }
}