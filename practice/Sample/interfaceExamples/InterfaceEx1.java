package interfaceExamples;

interface face
{
	void smile();
}

public class InterfaceEx1 implements face 
{
	public static void main(String args[])
	{
		InterfaceEx1 ex1 = new InterfaceEx1();
		ex1.smile();
	}
	 public void smile()
	{
		System.out.println("smile method ----->");
	}
}