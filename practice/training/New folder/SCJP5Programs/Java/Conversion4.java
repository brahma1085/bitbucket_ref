class Conversion4  {
    int x;
    public static void main (  String [] args ){
             final int i;
             i = 127;
             //byte b = (byte)i;   //ok
             byte b = i;  //compile-time err: possible loss of accuracy
             System.out.println(b);
    }
}
