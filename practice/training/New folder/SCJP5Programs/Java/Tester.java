import static java.lang.Math.*;

public class Tester {	
     Tester() {}

     public static void main(String[] args) {
                 Tester t = new Tester();
	System.out.println(Byte.MIN_VALUE);
	System.out.println(abs(Byte.MIN_VALUE));
	System.out.println(abs(Byte.MAX_VALUE));
                 System.out.println(abs(Integer.MAX_VALUE));
	int i=10;	
	i=++i;
	i=i++; //line 1
	System.out.println(i);	
     }
}   