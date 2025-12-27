public class Sequence {
 int j=10;

 public Sequence() {
   System.out.println(" constructor initializer : "+i); 
 }
 
 {
   // line x
   //System.out.println(" instance initializer :"+this.i);
  System.out.println(" instance initializer :"+j);
 }

 static
 {
    System.out.println(" static initializer"); 
 }

 public void method() {
    System.out.println(" method initializer"); 
 }

 public static void main(String args[]) {
    Sequence sq = new Sequence();
    sq.method();
 }

 int i=20;
}
