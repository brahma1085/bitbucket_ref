class Box{
    private int iVolume;
    Box(int iVolume){
	this.iVolume=iVolume;
    }
    public void finalize(){
	System.out.println("finalizing a Box");
    }
}

public class EdGrundy{
    boolean  stop  = false;
    public static void main(String argv[]){
	new EdGrundy();
    }
    EdGrundy(){
	while(stop==false){
	    new Box(99);
	}
    }
}


