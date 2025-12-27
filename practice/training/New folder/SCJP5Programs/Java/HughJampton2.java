class Richmond{
    
 Richmond(){
    System.out.print("Richmond");
 }
}
public class HughJampton2 extends Richmond{
    private int i;
    public static void main(String argv[]){
	new HughJampton2();
       }
    private HughJampton2(int i){
	this.i = i;

    }
 
   HughJampton2(){
   System.out.print("HughJampton");

    }


}




