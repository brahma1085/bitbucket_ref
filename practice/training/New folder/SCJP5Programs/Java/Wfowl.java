class Wchapel{
    String sDescription = "Aldgate";
    public String  getDescription(){
	return "Mile End";
    }
    public void output(int i ){
	System.out.println(i);
    }
}
interface Liz{

}
public class Wfowl extends Wchapel implements Liz{
    private int i = 99;
    public static void main(String argv[]){
	Wfowl wf = new Wfowl();
	wf.go();
    }
    public void go(){
	System.out.println(sDescription);
                 getDescription();
                 new Wfowl();
                 new Wfowl().output(i);
                 super.output(100);
                 class test implements Liz{}
    }
}



