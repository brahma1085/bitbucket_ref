class thread1 {	
     public static void main( String args[] ) throws Exception	{
	Thread t = new Thread() { 
                         public void run()	{ 
		try {
 		       System.out.println("hello") ;
		       sleep(5000);
 		}
		catch ( Exception e )	{
		       System.out.println("caught");	
                                   } 			
                        }
 	};
 	t.start();
	t.sleep(1000);
                  t.interrupt();
      }
}