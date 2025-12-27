interface face
{
	void smile();
}
class Interface1 implements face
{
	public static void main(String[] args) 
	{
		Interface1 evil_laugh = new Interface1();
		evil_laugh.smile();
	}

	void smile()
	{
		System.out.println("Muahahahahahhahaha");
	}
}
