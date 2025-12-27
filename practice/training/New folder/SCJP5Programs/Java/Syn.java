public class Syn{

    public static void main(String argv[]){
        Syn s = new Syn();
        s.amethod();
        s.amethod2();
        //amethod3();
        //amethod4();
    }

    public void amethod(){
         synchronized(this){
         }
    } 

    public synchronized void amethod2(){ }
    // public void synchronized amethod3(){ } //err
   // public synchronized void amethod4(void){ } //err
}

