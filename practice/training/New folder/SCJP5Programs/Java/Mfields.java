class Base{
    public int iAcres=3;
}
public class Mfields extends Base{
    private int iAcres=3.5;
    public static void main(String args[]){
	Base mf = new Mfields();
	System.out.println(mf.iAcres);
    }
}

