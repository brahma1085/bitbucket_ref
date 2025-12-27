package basics;

class Operator {
       public static void main(String[] args) {
            boolean b = false;
            char c = 'a';
            int i = 10;
            byte by = 45;
            long l = 2000l;
            float f = 123.82f;
            double d = 342.3422;
            double d1 = 45;

            //System.out.println("b > c " + b > c);  // error, cannot compare 
            //System.out.println("b != c " + b!=c);  // error, cannot compare
            //System.out.println("by == d1  " + by==d1);  // error, cannot compare String with double
            System.out.println("by == d1  " + (by==d1)); 
            //System.out.println("by == d1  " + 5.0==5l);
            System.out.println("by == d1  " + (5.0==5l));
 
            Integer iI = 10;
            System.out.println("Integer iI== i " + (iI == i));

            System.out.println("false | false & true=  " + (false | false & true));
            System.out.println("false | true & true=  " + (false | true &  true));
        }
}