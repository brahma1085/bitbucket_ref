class Base extends Integer{
    private String sName;
    Base(int i){
	super(i);
    }
    public void setName(String s){
	sName =s;
    }
    public String getName(){
	return sName;
    }

}
public class Haxby{
    public static void main(String argv[]){
	Haxby h = new Haxby();
	h.go();
    }
    public void go(){
	Base b = new Base(99);
	b.setName("monk");
	System.out.println(b.getName());
    }
}
