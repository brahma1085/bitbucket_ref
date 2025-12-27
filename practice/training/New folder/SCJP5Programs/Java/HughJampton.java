class Richmond{
    
 Richmond(){
    System.out.print("Richmond");
 }
}
public class HughJampton extends Richmond{
    private int i;
    public static void main(String argv[]){
	new HughJampton();
       }
    private HughJampton(int i){
	this.i = i;

    }
 
   HughJampton(){
   System.out.print("HughJampton");

    }


}

