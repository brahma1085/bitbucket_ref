public class HarHam{
	public static void main(String argv[]){
		HarHam hh = new HarHam();
		hh.go();
}
public void go(){
	String har = new String ("har");
	String ham = new String("har");
	collar:	
	for(int i=0; i < 2 ; i ++){
			if(har==ham){
			  break collar;
			}
			if(i > 0){
				continue collar;
			}
			
			System.out.print(i);
		}
	}

}