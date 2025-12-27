class Wrapper2 {
   public static void main(String[] args) 
   {
          Float f = new Float(3.14);
          f = new Float("3.14");
          //Integer i = new Integer("3.14");  //Runtime error "NumberFormatException"
          //Integer i = 3.14;   // Compile-time error: find "double", need "java.lang.Integer"
          Integer i = (int)3.14;
          Integer j = (Integer)3.14;
          System.out.println("i="+i);
   }
}
package lara.examples;

public class PassByValExample 
{
	int k1;
	public static void main(String[] args) 
	{
		int k=6;
		PassByValExample ex = new PassByValExample();
		System.out.println("value of i in main method ---->"+k);
		ex.test(k);
		System.out.println("val of i after call to the method ---->"+k);
		// value of i in the pass by value can not be changed
		
		//pass by ref --->
		ex.test1(ex);
		ex.test4(ex.k1);
		System.out.println("value of k is ------> "+ex.k1);
		
	}
	void test(int i)
	{
		System.out.println("value of i is ---->"+i);
		i = i +1;
		System.out.println("modified value of i is ---->"+i);
	}
	void test1(PassByValExample ex)
	{
	//	String[] a = new String[10];
		System.out.println("value of k is "+ex.k1);
		ex.k1 =10;
		System.out.println("value of k is ---->"+ex.k1);
		ex.test(5);
		ex = new PassByValExample();
	//	ex.main(a);
	}
	void test4(int k1)
	{
		k1 = k1 + 4;
		System.out.println("k1 val is ---->"+k1);
	}
}

output:
value of i in main method ---->6
value of i is ---->6
modified value of i is ---->7
val of i after call to the method ---->6
value of k is 0
value of k is ---->10
value of i is ---->5
modified value of i is ---->6
k1 val is ---->14
value of k is ------> 10

package lara.examples;
import java.sql.Date;
public class MultiDimEx 
{
	double[] b;
	float[] f = new float[10];
	int[][] mulDimension = new int[1][1];
	float[][] ff = new float[1][2];
	public static void main(String[] args) 
	{
		MultiDimEx mulDim = new MultiDimEx();
		int[] a = new int[10];//this creates an array object on the heap
		//Date d;
		System.out.println("Array obj b is created ---->"+mulDim.b);
		System.out.println("Array obj a is created ---->"+a);
		System.out.println("Array obj f is created ---->"+mulDim.f);
		for(int i=0;i<10;i++)
		{
			System.out.println("value of float array ----->"+mulDim.f[i]);
			//System.out.println("val of double array --->"+mulDim.b[i]);//Null Pointer Exception
			System.out.println("values of integer array----->"+a[i]);
		}
		mulDim.mulDimension[0][0]=1;
		mulDim.mulDimension[0][1]=2;
		mulDim.mulDimension[1][0]=3;
		mulDim.mulDimension[1][1]=4;
		
		System.out.println("values of multi dimensional array ---->"+mulDim.mulDimension[0][0]);
		System.out.println("values of multi dimensional array ---->"+mulDim.mulDimension[0][1]);
		System.out.println("values of multi dimensional array ---->"+mulDim.mulDimension[1][0]);
		System.out.println("values of multi dimensional array ---->"+mulDim.mulDimension[1][1]);
		System.out.println("2 dim ---->"+mulDim.mulDimension[2][0]);
		System.out.println("2 dim ---->"+mulDim.mulDimension[2][1]);
		//System.out.println("2 dim ---->"+mulDim.mulDimension[2][2]);
	//	mulDim.ff[0][0] = 1;
	//	System.out.println("float 2 dim array ---->"+mulDim.ff[0][0]);
	}
}

output:
Array obj b is created ---->null
Array obj a is created ---->[I@3e25a5
Array obj f is created ---->[F@19821f
value of float array ----->0.0
values of integer array----->0
value of float array ----->0.0
values of integer array----->0
value of float array ----->0.0
values of integer array----->0
value of float array ----->0.0
values of integer array----->0
value of float array ----->0.0
values of integer array----->0
value of float array ----->0.0
values of integer array----->0
value of float array ----->0.0
values of integer array----->0
value of float array ----->0.0
values of integer array----->0
value of float array ----->0.0
values of integer array----->0
value of float array ----->0.0
values of integer array----->0
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 1
	at lara.examples.MultiDimEx.main(MultiDimEx.java:24)

package lara.examples;
public class WrapperExample 
{
	public static void main(String[] args)
	{
		String a = "banu";
		String k = "1234";
		// a++; gives an error due to it can not convert from String to Int
		// it is like adding a obj reference
		// this can be done thru Wrapper Classes
	//	int i = Integer.parseInt(a); // can not be parsed, NumberFormatException! as String(banu) can not be converted to a int
		// i++;
		//System.out.println("i value is ---->"+i);
		// k++; can not convert from String to a int, So
		int k1 = Integer.parseInt(k); // this is how Wrapper class is used to convert from String to Integer,
		// ||ly we can convert to Float, Short, Long, Double, etc
		k1++;
		System.out.println("value of k1 is ---->"+k1);
		int i = 34;
		String str = Integer.toString(i); // to convert integer to String we use WC Integer,
		//// ||ly we can convert to Float, Short, Long, Double, etc to String
		str = str + "banu";
		System.out.println("The String is ---->"+str);
		
		//To convert a Derived data type to primitive
		Integer ii = new Integer(1);
		// ii++;
		int k12 = ii.intValue(); //Derived data type to primitive //is unboxing
		k12++;
		System.out.println(k12);
		ii = new Integer(k12); //Primitive to derived data type //is auto boxing
		System.out.println("Primitive --- Derived"+ii);
			
		Integer i10 = new Integer("1");
		i10.toString();
		i10.valueOf(10);
		Integer ii1 = new Integer(1000);
		Integer ii2 = new Integer(1000);
		
		//Since Wrapper classes are immutable, they point to different objects
		
		System.out.println(ii1.compareTo(ii2));
		System.out.println(ii1.valueOf(123));
		if(ii1==ii2)
		{
			System.out.println("ii1 == ii2");
		}
		if(ii1.equals(ii2))
		{		
			System.out.println("Pointing to different objects, but checks that contents are equal or not");
		}
		//System.out.println(String.format("%,d", new Double(4.003));
		Float f = new Float(12.34);
		Float f1 = new Float("1.34");
//		Integer ii10 = new Integer(12.34); //gives a compilation error, b'coz there is no constructor of Integer(double)
		Integer ii11 = new Integer("12.34"); // gives a runtime exception as NumberFormatException, here it can not convert from the double to integer
		// int t = (int) ii11; can not cast from Integer to int
		int p =3;
		// Integer k1k = (Integer)p;
		// Integer k1k = Integer.valueOf(p);
		// Integer ik = (int)3.14;
		// Integer jj = (Integer) 3.14;
	}
}
// While Converting a String to Primitive data type, we may get a Exception name NumberFormatException

output:
value of k1 is ---->1235
The String is ---->34banu
2
Primitive --- Derived2
0
123
Pointing to different objects, but checks that contents are equal or not
Exception in thread "main" java.lang.NumberFormatException: For input string: "12.34"
	at java.lang.NumberFormatException.forInputString(Unknown Source)
	at java.lang.Integer.parseInt(Unknown Source)
	at java.lang.Integer.<init>(Unknown Source)
	at lara.examples.WrapperExample.main(WrapperExample.java:56)
