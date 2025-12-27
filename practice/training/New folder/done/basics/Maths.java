package basics;

import static java.lang.Math.*;

class Maths {
    public static void main(String[] args) {
        double[] arr = {3.45, 3.56, -3.45, -3.56};
        
        for (double d : arr)  {
           System.out.printf("ceil(%f)=%f    ", d, ceil(d));
           System.out.printf("floor(%f)=%f   ", d, floor(d));
           System.out.printf("round(%f)=%d   ", d, round(d));
           System.out.println();
        }
        //System.out.printf("%f", 10);  //runtime error

        int i = -1;
        System.out.println((i<0)?-i:i);   // ouput is "1"

        System.out.println((Math.abs(Integer.MIN_VALUE)));

	int i2 = 1/2;    //ok 0
        float f = 1/2;   
	System.out.println(f);   //0.0

        int i3=1; 
        int j3=i3++; 
        if((i3>++j3)&&(i3++==j3)){ 
             i3+=j3; 
        } 
        System.out.println(i3 + " " + j3);  //output: 2, 2
    }
}