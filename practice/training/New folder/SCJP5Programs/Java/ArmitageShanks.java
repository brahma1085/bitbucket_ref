class Harpic{
    public void finalize(){
	System.out.println("Harpic");
    }

}

public class ArmitageShanks{
    Harpic har;
    public static void main(String argv[]){
	ArmitageShanks as = new ArmitageShanks();
	as.oui();
    }
    public void oui(){
	har = new Harpic();
	mno(har);
	//one
	har=null;

    }
    public void mno(Harpic har){
	Harpic pic=har;
	//two
	pic=null;
	//three
	har=null;
    }
}

