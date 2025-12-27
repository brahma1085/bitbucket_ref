import java.util.*;

class GenericConversion1 {
	public static void main (  String [] args )
	{
		List < String > least = new ArrayList < String > ();
		List list = new ArrayList();
		meth(list);  // warning: have List but need List<String>
		seth(least);
	}
	public static void meth(List < String > list)
	{
		System.out.println("List");
	}
	public static void seth(List list)
	{
		System.out.println("Mist");
	}
}
