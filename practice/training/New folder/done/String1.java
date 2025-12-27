public class String1 {
    public static void main(String[] args) {
      String str1 = "Test";
      String str2 = "";
      String str3 = "  ";
      System.out.println(str1 == (str1+str2));               // false
      System.out.println(str1 == str1.concat(str2));      // true
      System.out.println(str1 == str1.concat(str3));      //false
      //If the string literals being compared are already present in the String Pool, 
      //they are interned automatically
      String s="java"; 
      System.out.println(s==("j" + "a" + "v" + "a"));   // true
   }
}

/*The reason the first one is false is that even though "Test" and "" are in the String pool, 
   str1+str2 is not interned so a new object is created.
   The reason the second one is true is that according to the API docs for String, if the length
   of the argument to concat is 0, then the String that called the method is returned.
*/

