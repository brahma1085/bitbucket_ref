class Method1
{
	public static void main(String[] args) 
	{
		Method1 inst_test = new Method1();
		int i1 = 2000;
		int i2 = 2000;
		int i3 = 2;
		int i4 = 2;
		Integer Ithree = new Integer(2); // 1
		Integer Ifour = new Integer(2); // 2
		System.out.println( Ithree == Ifour );
		inst_test.method( i3 , i4 );
		inst_test.method( i1 , i2 );
		
	}
	public void method( Integer i , Integer eye )
	{
		System.out.println(i == eye );
	}
}
