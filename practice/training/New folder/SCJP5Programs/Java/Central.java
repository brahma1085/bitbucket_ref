class Trebble{
    int i = 99;
}
class Base extends Trebble{
    int i =100;
}
public class Central extends Base{
    public static void main(String argv[]){
	Central c = new Central();
	c.wynyard();
    }
    public void wynyard(){
	Trebble t = new Central();
	Base b = (Base) t;
	System.out.println(b.i);
    }
	
}


