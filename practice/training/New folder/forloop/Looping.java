package forloop;

import java.util.*;
import static java.lang.System.out;

public class Looping {
    public static void main(String[] args) {
	List<Double> versions = new ArrayList<Double>();
	versions.add(1.0); versions.add(1.5); versions.add(2.0);
	//for (Double v : versions.values()) {  //compiler error
	for (Double v : versions) {   //ok
	     v = v + 0.5;
	}
	out.println(versions);
    }
}