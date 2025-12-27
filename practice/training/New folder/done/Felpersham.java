public class Syn extends Town{

    public static void main(String argv[]){
        amethod();
        amethod2();
        amethod3();
        amethod4();
    }

    public void amethod(){
	synchronized(this){
	}
    } 

    public synchronized void amethod2(){ }
    public void synchronized amethod3(){ }
    public synchronized void amethod4(void){ }
}

