package io;

import java.util.Scanner;

class Scanners {
       public static void main(String[] args) {
           Scanner sc = new Scanner("123 A 3b c,45, x5x,76 82 L");
           do {
	if(sc.hasNextInt()) System.out.print(sc.nextInt() + " ");
                 else sc.next();
           } while ( sc.hasNext() );

           sc = new Scanner("123 A 3b c,45, x5x,76 82 L").useDelimiter(" ");
           while(sc.hasNext()) {
                 if(sc.hasNextInt()) System.out.print(sc.nextInt() + " ");
                 else sc.next(); 
           }
       }
}