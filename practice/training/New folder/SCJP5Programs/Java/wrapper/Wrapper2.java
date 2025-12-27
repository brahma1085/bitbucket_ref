class Wrapper2 {
   public static void main(String[] args) {
          Float f = new Float(3.14);
          f = new Float("3.14");
          //Integer i = new Integer("3.14");  //Runtime error "NumberFormatException"
          //Integer i = 3.14;   // Compile-time error: find "double", need "java.lang.Integer"
          Integer i = (int)3.14;
          Integer j = (Integer)3.14;
          System.out.println("i="+i);
   }
}
