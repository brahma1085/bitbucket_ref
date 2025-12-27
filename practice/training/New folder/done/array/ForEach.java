package array;

class ForEach{
    public static void main(String args[]){
	  byte arr[]=new byte[]{2,3,4};
          //for(final byte i : getCharArray(arr))   // compile-err: loss of accuracy
          //for(final short i : getCharArray(arr))  // compile-err: loss of accuracy
          //for(final char i : getCharArray(arr))   //output is strange, unless "System.out.print((int)i);" 
          //for(final long i : getCharArray(arr))   //ok
          //for(final float i : getCharArray(arr))  //ok
 	  for(final int i : getCharArray(arr))      //ok
 	      System.out.print(i);  
    }

    static char[] getCharArray(byte[] arr){
	char[] carr=new char[4];
	int i=0;
	for(byte c : arr){
	    carr[i]=(char)c++;
	    i++;
        }
	return carr;
    }
}
