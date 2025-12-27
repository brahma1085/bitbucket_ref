class Biddy{
    public Biddy(){
	System.out.print(" Biddy ");
    }
}
class Val extends Biddy{
    public Val(){
	System.out.print(" Val ");
    }
}

public class BluePeter extends Val{
    public static void main(String argv[]){
	BluePeter bp = new BluePeter();
	bp.shep(3);
    }
    public void shep(float i){
	System.out.println(i);
    }
}

