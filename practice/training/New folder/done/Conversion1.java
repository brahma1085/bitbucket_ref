class Conversion1 {
    public static void main(String[] args) {
         //byte b1=128;   // compile err: the constant should be between -128 and 127

         byte b2 = (byte)2+4;  //constant, ok

         byte b3=2;
         // byte b4 = b3+7;  //compile-time err
         byte b4 = (byte)(b3+7);
         System.out.println("b4="+b4);

        /* From char to short and short to char, need cast, otherwise compile-err
        char c1='\uc000';
        short s = (short)c2;  // -16384  
        System.out.println("s="+s);
        */
 
        /* Runtime NullPointerException
        Integer ii = null;
        int i1 = ii;  
        */

        /* Array
        int[] arr1 = new int[3];
        int[] arr2 = new int[4];
        arr1 = arr2;   //ok
        Integer[] arr3 = new Integer[3];
        arr1 = arr3;    // compile-time err: there are not of the same type
        arr3 = arr1;    // compile-time err: there are not of the same type

        byte[] arr4 = new byte[3];
        //arr1 = arr4;   // compile-time err: there are not of the same primitive type
        */

       byte b5=0x7f;
       char c2='G';
       int i2 = b5&c2;   //ok
       //byte b6 =b5&c2;  //compile-time err: might lost accuracy
       byte b6 =(byte)(b5&c2);   //ok

       Byte b7 = new Byte(10);   //compile-time error: method conversion has no narrowing for integer
       Byte b8 = new Byte((byte)10);   //ok
     }
}