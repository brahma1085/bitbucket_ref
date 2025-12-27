class Class5 {
     final int i=1;
     int j = i;
     {
          j = 10;
      }
     public static void main(String[] args) {
          //Class5 cl = new Class5();         
          //cl.j = 10;   // compile-time err
    }
}