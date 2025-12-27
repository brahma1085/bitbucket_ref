package basics;

import java.lang.*;

public class NaN1{
    public static void main(String[] arg){

   	Double a = new Double(Double.NaN);
	Double b = new Double(Double.NaN);
	//System.out.println("Double.Nan value in double "+a.doubleValue() + 

	b.doubleValue();

	if(a.doubleValue() == b.doubleValue()) //compare 1
		System.out.println("doubleValue method:Equal");
	else
		System.out.println("doubleValue method:not Equal");

	if(Double.NaN == Double.NaN) //compare 2
		System.out.println("Double.Nan is equal to Double.NaN");
	else
		System.out.println("Double.Nan is not equal to Double.NaN");

	if(a.equals(b))//compare 3
		System.out.println("equal");
	else
		System.out.println("Not equal");
	}
}

/* According to the API documentation for Double, "If d1 and d2 both represent Double.NaN, 
then the equals method returns true, even though Double.NaN==Double.NaN has the value false."
http://java.sun.com/j2se/1.5.0/docs/api/java/lang/Double.html
*/