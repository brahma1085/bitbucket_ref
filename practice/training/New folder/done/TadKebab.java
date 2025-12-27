public class TadKebab{
    public static void main(String argv[]){
	new TadKebab();
    }
    private TadKebab(){
	String[] Sa = {"Sam Smith", "John Smith"};
	getName(Sa);
	System.out.print(Sa[0]);
    }

    public void getName(String s[]){
	s[0]="Coors";	
    }
}

