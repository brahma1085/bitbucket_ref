abstract class Base{
    int iSize;
    public int getAge(){
	return iSize; 
    }

}
class Jewel{
    private int iAge=99;
}

public class KDierden extends Jewel{

    public static void main(String argv[]){
	new KDierden();
    }
    private KDierden(){
	System.out.print(iAge);
    }

}




