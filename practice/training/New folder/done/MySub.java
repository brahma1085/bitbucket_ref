class MySuper {
   public MySuper(int i) {
     System.out.println("super " + i);
   }
}

 public class MySub extends MySuper {
   public MySub() {
     super(2);
     System.out.println("sub");
  }

  public static void main(String [] args) {
     MySuper sup = new MySub();
  }
}
