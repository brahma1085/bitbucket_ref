class FinalSwitch1 {
     public static void main (  String [] args ) {
          Integer i = 4;
          final Integer x1 = i;   
          i=6;     // x1 is still 4. why?
          

          int[] arr = {3, 4,5};
          final int[] x4=arr;
          arr[2] = 6;    //now x4[2] = 6

          final int x = 10;
    
          switch ( x ) {   //ok
	//case x4:    // compile-time err:  need constant expression
                 //case 10:    // compile-time err: label repition
                case 5:
	       System.out.println("5");
	       break;
	case x:
	       System.out.println("x");
	       System.out.println("x1="+x1);
                        System.out.println("x4[2]="+x4[2]);
	       break;
          }
     }
}

/*Final objects are not allowed in a case statement. 
A  final object¡¯s value can be changed whereas a final variables value cannot be changed. 
*/