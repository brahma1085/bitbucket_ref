public class Brand2Hand implements Runnable{
   boolean bStop;
   public static void main(String argv[]){
	Brand2Hand b2h = new Brand2Hand();
	b2h.start();
	Brand2Hand b2h2 = new Brand2Hand();
	b2h2.bStop=true;
	
    }
    public void start(){
	for(int i = 0; i < 100; i ++){
	    System.out.println(i);
	    if (bStop ==true){
		break;
	    }
	}
       
    }
}
