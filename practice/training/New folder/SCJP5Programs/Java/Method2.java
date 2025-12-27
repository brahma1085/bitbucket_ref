class Method2
{
	public static void main(String[] args) 
	{
		Method2 inst_test = new Method2();
		inst_test.method ( 1 , 1 , 1);
		inst_test.method( new Integer(1) , new Integer(2) , new Integer(3) );
		inst_test.method ( 1 , new Integer(5) );
		inst_test.method ( new Integer(10) , 1 );
	}

	public void method( Integer... I )
	{
		System.out.println("Eye in the sky");
	}

	public void method( int... i )
	{
		System.out.println("Fly in the pie");
	}

}

/*To the compiler, Integer.. and int... are pretty much the same. This results in an 
   ambiguous state, which results in an error. The JVM wont know which method to call if 
   this is allowed to be compiled.
*/