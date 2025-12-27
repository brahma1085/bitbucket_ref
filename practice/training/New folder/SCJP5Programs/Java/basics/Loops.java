package basics;

class Loops {
      public static void main(String [] args) {
          //testUnreachable();
          //testVariableScope();
          //testIllegalForEach();
          //testLabel();
          testLabel2();
          testLabel3();
       }

       static void testUnreachable() {
          /*
          while(false){
              System.out.println("Great");  // Unreachable statement
          }*/
       }

       static void testVariableScope() {
          /*int x1 = 0, y1=12;
          do {
             int y = 12;
          } while (x1++ < y);  // compile-time err: y not declared
          System.out.println(x1); */
       } 


       static void testIllegalForEach() {
          int[] x = {7,6,5,4,3,2,1};
          /*
          int y = 0;  
          for(y : x) {  // illegal statement
               System.out.print(y + " ");
          }*/

          for(int y=0, z=0; z<x.length; z++) { 
               y = x[z];
               System.out.print(y + " ");
          }

          /* 
          for(int y=0, int z=0; z<x.length; z++) {   // illegal int z=0;
               y = x[z];
               System.out.print(y + " ");
          }*/

          int y1 = 0; 
          for(int z=0; z<x.length; z++) { 
               y1 = x[z];
               System.out.print(y1 + " ");
          }
       }

       //----------------------------------
       static void testLabel() {
          int i=0,j=5; 
          tp:  
          for(;;i++){  
             for(;;--j) 
                if(i>j)break tp; 
          }
          System.out.println("i="+i+",j="+j);   // i=0, j=-1
       }

       //---------------------------------
       static void testLabel2() {  //ok
          int i=0,j=5;
          out: for(;;){
                  i--;
		  inner: for(;;){
		            if(i>j--)  
                               break out;
		         }
	       }
          System.out.println("i="+i+",j="+j);   // i=-1, j=-3
       }  

       //---------------------------------
       static void testLabel3() {   //ok
          int i=0,j=5;
          out: while (true){
                  i--;
		  inner: while (true) {
		            if(i>j--)  
                                break out;
		         }
	       }
          System.out.println("i="+i+",j="+j);   // i=-1, j=-3
       }        
}