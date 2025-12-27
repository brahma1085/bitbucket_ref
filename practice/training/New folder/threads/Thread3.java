package threads;

class Thread3 extends Thread { 
  public void run(){ 
     System.out.println(" Inside run "); 
     synchronized( this ){ 
     	System.out.println(" Inside synchronized ");   
     } 
  } 
  public static void main( String[] args ) 
  { 
  	//------------------------------ 
	//new Thread3().start();  //1 
	//new Thread3().start();  //2 
        /*  
  	 OUTPUT : 
         Inside run 
	 Inside run 
	 Inside synchronized 
	 Inside synchronized 
        */

	//----------------------------- 
	Thread3 t = new Thread3();  
  	new Thread(t).start();      //3 
	new Thread(t).start();      //4 
	/*  
        OUTPUT : 
	 Inside run 
	 Inside synchronized 
	 Inside run 
	 Inside synchronized 
	*/ 

        //-----------------------------
	Thread3 t = new Thread3();  
  	new Thread(t).start();              //5 
	new Thread(new Thread3()).start(); //6 
        /* 
	OUTPUT 
	 Inside run 
	 Inside synchronized 
	 Inside run 
	 Inside synchronized 
	*/ 
	//------------------------------ 
  } 
}

