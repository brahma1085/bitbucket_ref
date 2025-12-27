package basics;

public class Null {
     private String title; // instance reference variable
     public String getTitle() {
	return title;
     }

      public static void main(String [] args) {
	Null b = new Null();
	System.out.println("The title is " + b.getTitle());   // output is:The title is null

                  String s=null;
                  System.out.println(s);  //output is: null

                  int[] arr = new int[5];
                  for (int i: arr)
                        System.out.println(i);
      }
}