class Archer {
    public void finalize(){
	System.out.println("finalizing");
    }

}
public class Shula{
    String[] sa= new String[10];
    public static void main(String argv[]){
	new Shula();
    }
    public Shula(){
	String s = new String("Shula");
	int i = 99;
	i = null;
	Archer a = new Archer();
	a=null;
	System.gc();

	
    }
}
