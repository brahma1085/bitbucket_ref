public class Class1 extends newtreeset {
    public static void main(String [] args) {
         Class1 t = new Class1();
         t.count();
   }
}

protected class newtreeset {   // error here
   void count() {
        System.out.println("yes");
   }
} 

/*A top level class can be either Abstract or Final or Public or a default class. 
We cannot have a top level class with protected or Private access modifiers!!
*/