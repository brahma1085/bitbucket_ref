package com.scb.gl.actions;



import general.Validations;

import java.util.StringTokenizer;

public class NumFormat{
  private static final String[] majorNames = {
    "",
    " Thousand",
    " Million",
    " Billion",
    " Trillion",
    " Quadrillion",
    " Quintillion"
    };

  private static final String[] tensNames = {
    "",
    " Ten",
    " Twenty",
    " Thirty",
    " Fourty",
    " Fifty",
    " Sixty",
    " Seventy",
    " Eighty",
    " Ninety"
    };

  private static final String[] numNames = {
    "",
    " One",
    " Two",
    " Three",
    " Four",
    " Five",
    " Six",
    " Seven",
    " Eight",
    " Nine",
    " Ten",
    " Eleven",
    " Twelve",
    " Thirteen",
    " Fourteen",
    " Fifteen",
    " Sixteen",
    " Seventeen",
    " Eighteen",
    " Nineteen"
    };
    
	private String ConvertLessThanOneThousand(int number)
  	{
    	String soFar="";
	
    	if (number % 100 < 20)
    	{	
        	soFar = numNames[number % 100];
        	number /= 100;
       	}
    	else
    	{
	        soFar = numNames[number % 10];
    	    number /= 10;
	        soFar = tensNames[number % 10] + soFar;
        	number /= 10;
       	}
    	if (number == 0) return soFar;
    	return numNames[number] + " hundred" + soFar;
  	}

	private String ConvertToCrore(int number)
	{
		String soFar="",lakhoption="",croreoption="";
		int n=number/100,place=0;
		
		if(n>=1)
		{
			n=number/10000000;	
			if(n!=0)
			{
				croreoption = ConvertToCrore(n);
				number%=10000000;
			}
			n=number/100000;
			if(n!=0)
			{
				lakhoption = ConvertToLakh(n);
				number%=100000;
			}
			do
    		{
    			n = number % 1000;
      			if (n != 0)
      			{
	        		String s = ConvertLessThanOneThousand(n);
	         		soFar = s + majorNames[place] + soFar;
    	    	}
      			place++;
      			number /= 1000;
    		}while (number > 0);
    	}
		else
		{	
			if(number<20)
				soFar = numNames[number];
			else
			{
				soFar = numNames[number%10];
				number/=10;
				soFar = tensNames[number]+soFar;
			}
		}
		return croreoption+lakhoption+soFar+" crore";	
	}

	private String ConvertToLakh(int number)
	{
		String soFar="";
		if(number<20)
			soFar = numNames[number];
		else
		{
			soFar = numNames[number%10];
			number/=10;
			soFar = tensNames[number]+soFar;
		}	
		return soFar+" lakh";	
	}

	
	/**
	 * @ Code Changed by sanjeet...
	 *
	 * this was done by taking suraj's help
	 * on 5/03/2006 only because there was problem with displaying numbers
	 * after decimal point....
	 */
	
	
	/*public String Convert(double num)
	{
    	String soFar = "",croreoption="",lakhoption="",prefix="",paise="";
    	int place = 0;
    	long number=number =(long)num,n;
    	String str=String.valueOf(num-number);
    	int a=Integer.parseInt(str.substring(str.indexOf(".")+1));
		if(a > 0) {
		    if(Integer.parseInt(str.substring(str.indexOf(".")+2,str.indexOf(".")+4))>=5)
		        a=a+1;
		    if(a>0)	
		        paise = " and "+ConvertLessThanOneThousand(a)+" paise";
		} 
		if (number == 0)
    		return "zero";
    	if (number < 0)
    	{
    		number = -number;
        	prefix = "negative";
    	} 
	
		n= number/10000000;
		if(n!=0)
		{
			croreoption = ConvertToCrore((int)n);
			number%=10000000;	
		}
		n=number/100000;
		if(n!=0)
		{
			lakhoption = ConvertToLakh((int)n);
			number%=100000;
		}
	
    	do
    	{
    		n = number % 1000;
      		if (n != 0)
      		{
	        	String s = ConvertLessThanOneThousand((int)n);
    	     	soFar = s + majorNames[place] + soFar;
        	}
      		place++;
      		number /= 1000;
    	}while (number > 0);
	
    	return (prefix + croreoption+lakhoption+soFar+paise+" only").trim();
	}*/

	/**
	 * @  New Code Added by sanjeet...
	 * 
	 */
	
	public String Convert(double num)
	{
    	String soFar = "",croreoption="",lakhoption="",prefix="",paise="";
    	int place = 0;
    	long number=number =(long)num,n;
    	System.out.println("num"+num);
    	
    	String mainstr= Validations.changeFloat(num);
    	System.out.println("mainstr"+mainstr);
    	StringTokenizer st= new StringTokenizer(mainstr,".");
    	String firststr=null;
    	String str=null;
    	
    	if(st.hasMoreElements())
		{
    		firststr=st.nextToken();
    		str=st.nextToken();
		}
    	
    	System.out.println("str is"+str +"nunber is"+number+"num is"+num+"first is"+firststr);
    	
        int a=Integer.parseInt(str);
    	System.out.println("a is"+a);
    	
    	{
		    if(a>0)	
		       paise = " and "+ConvertLessThanOneThousand(Integer.parseInt(String.valueOf(a)))+" paise";
		    
		    System.out.println("**"+paise);
		} 
		if (number == 0)
    		return "zero";
    	if (number < 0)
    	{
    		number = -number;
        	prefix = "negative";
    	} 
	
		n= number/10000000;
		if(n!=0)
		{
			croreoption = ConvertToCrore((int)n);
			number%=10000000;	
		}
		n=number/100000;
		if(n!=0)
		{
			lakhoption = ConvertToLakh((int)n);
			number%=100000;
		}
	
    	do
    	{
    		n = number % 1000;
      		if (n != 0)
      		{
	        	String s = ConvertLessThanOneThousand((int)n);
    	     	soFar = s + majorNames[place] + soFar;
        	}
      		place++;
      		number /= 1000;
    	}while (number > 0);
	
    	return (prefix + croreoption+lakhoption+soFar+paise+" only").trim();
	}
	
}
    
  
