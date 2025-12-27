package com.scb.loans.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;

import com.scb.designPatterns.LoansDelegate;


import exceptions.DateFormatException;
import general.Validations;

public class Calculation {

	private static FileAppender file_appender = null;
	private static Logger file_logger = null;
	LoansDelegate delegate = null;
	
Object [][] calculateReducingInterest(double prn,double rate,int period)
{
	Object data[][]=new Object[period+1][6];
	try{
	GregorianCalendar g=new GregorianCalendar();	
	String str1=delegate.getSysDate();
	double sumi=0.0,sumt=0.0;
	double install=Math.round(prn/period);
	double bal=Math.round(prn-(period*install));
	
	int i=0;
	for(i=0;i<period;i++)
	{
		g.add(Calendar.MONTH,1);
		String str=Validations.checkDate(g.get(Calendar.DATE)+"/"+(g.get(Calendar.MONTH)+1)+"/"+g.get(Calendar.YEAR));
		double interest=Math.round((prn*rate*(Validations.dayCompare(str1,str)))/36500);
		str1=str;
		if(i==(period-1))
			install=install+bal;

		prn=prn-install;


		data[i][0]=String.valueOf(i+1);
		data[i][1]=str;
		data[i][2]=String.valueOf(install);
		data[i][3]=String.valueOf(interest);
		data[i][4]=String.valueOf(interest+install);
		data[i][5]=String.valueOf(prn);	

		sumi+=interest;
		sumt+=(interest+install);

	}
	data[i][0]="";
	data[i][1]="";
	data[i][2]="";
	data[i][3]=String.valueOf(sumi);
	data[i][4]=String.valueOf(sumt);
	data[i][5]="";	


	}catch(Exception ex){System.out.println(ex);}
	return data;
}



Object [][] calculateEMIInterest(double prn,double rate,int period)
{
	Object data[][]=new Object[period+1][6];
	double cprate=rate,sumi=0.0,sumt=0.0,cin=0.0;

	GregorianCalendar g=new GregorianCalendar();	
	String str1=delegate.getSysDate();
	rate=rate/1200;

	double pow=1;
	for(int j=0;j<period;j++)
		pow=pow*(1+rate);

	double install=Math.round(0.01*Math.round(100*(prn*pow*rate)/(pow-1)));
	//double bal=Math.round(prn-(period*install));
	int i=0;

	for(i=0;i<period;i++)
	{
		g.add(Calendar.MONTH,1);
		String str=g.get(Calendar.DATE)+"/"+(g.get(Calendar.MONTH)+1)+"/"+g.get(Calendar.YEAR);
		double interest=Math.round((prn*cprate*(Validations.dayCompare(str1,str)))/36500.00);

		str1=str;
		cin=(install-interest);
		if(i==(period-1))
		{
			cin=(interest+prn);			
			install=cin;
		}

/*		if(prn<install)
		{
			install=prn;
			prn=0;

		}*/

		data[i][0]=String.valueOf(i+1);
		data[i][1]=str;
		data[i][2]=String.valueOf(install);
		data[i][3]=String.valueOf(interest);
		data[i][4]=String.valueOf(cin);
		data[i][5]=String.valueOf(prn);	
		prn=prn-(install-interest);
		
		sumi+=interest;
		sumt=sumt+(install-interest);

		}
	data[i][0]="";
	data[i][1]="";
	data[i][2]="";
	data[i][3]=String.valueOf(sumi);
	data[i][4]=String.valueOf(sumt);
	data[i][5]="";	

	return data;
}

List adjustReducingInterest(double prn,double install,double rate,int period,String prev,String effective,int start)
{
	System.out.println("Inside adj Reducing...");
	System.out.println("Prn : "+prn);
	System.out.println("Install : "+install);
	System.out.println("Rate: "+rate);
	System.out.println("period: "+period);
	System.out.println("Prev: "+prev);
	System.out.println("Eff Dt: "+effective);
	System.out.println("Start : "+start);
	Vector vec=new Vector(1,1);
	Map map_obj = new TreeMap();
	List list_obj = new ArrayList();
	try{
		StringTokenizer st=new StringTokenizer(effective,"/");
		int d=Integer.parseInt(st.nextToken());
		int m=Integer.parseInt(st.nextToken())-2;
		int y=Integer.parseInt(st.nextToken());
		GregorianCalendar g=new GregorianCalendar(y,m,d);


		double sumi=0.0,sumt=0.0;
		double bal=Math.round(prn-(period*install));
		String current=effective;
		
		int i=0;
		for(i=0;i<period;i++)
		{
			g.add(Calendar.MONTH,1);
	
			if(i!=0)
				current=Validations.checkDate(g.get(Calendar.DATE)+"/"+(g.get(Calendar.MONTH)+1)+"/"+g.get(Calendar.YEAR));

			double interest=Math.round((prn*rate*(Validations.dayCompare(prev,current)))/36500);

			if(interest<0 || prn<=0)
				break;

			prev=current;
	
		// 	if(i==(period-1)) // Code changed by Murugesh on 17/1/2006
			if(i==(period-1) && bal>0) // Code added by Murugesh on 17/1/2006
				{
					install=bal+prn;
					System.out.println("Inside the if condition "+install+"bal"+bal+"prn"+prn);
					prn=0; // Code added by Murugesh on 17/1/2006
				}
			else
			{
				if(prn<install)
				{
						install=prn;
						prn=0;
				}
				else
					prn=prn-install;				
			}				
			System.out.println("Principal balance="+prn+"  "+install);			

			/*Object data[]=new Object[6];		
			data[0]=String.valueOf(start++);
			data[1]=current;
			data[2]=String.valueOf(install);
			data[3]=String.valueOf(interest);
			data[4]=String.valueOf(interest+install);
			data[5]=String.valueOf(prn);*/	
			//vec.addElement(data);
			map_obj.put("id",start);
			map_obj.put("LoanTrandate",current);
			map_obj.put("PrincipalPaid",install);
			map_obj.put("IntPaid",install);
			map_obj.put("TranAmt",(interest+install));
			map_obj.put("LoanBal",prn);
			list_obj.add(map_obj);
			

			sumi+=interest;
			sumt+=(interest+install);
			
		}
		System.out.println("Sumi "+sumi);
		System.out.println("Sumt "+sumt);
		/*Object data[]=new Object[6];		
		data[0]="";
		data[1]="";
		data[2]="";
		data[3]=String.valueOf(sumi);
		data[4]=String.valueOf(sumt);
		data[5]="";	
*/		//vec.addElement(data);
		map_obj.put("id",start);
		map_obj.put("LoanTrandate",current);
		map_obj.put("PrincipalPaid",install);
		map_obj.put("IntPaid",sumi);
		map_obj.put("TranAmt",sumt);
		map_obj.put("LoanBal",prn);
		list_obj.add(map_obj);

		
		
		
	
	/*Iterator itr=vec.iterator();
	while(itr.hasNext())
	{
		System.out.println("Value: "+Double.parseDouble(itr.next().toString()));
	}*/
	
	System.out.println("End of adj Redu...");
	}catch(Exception ex){ex.printStackTrace();}
	return list_obj;
}


List adjustEMIInterest(double prn,double install,double rate,int period,String prev,String effective,int start)
{
	System.out.println("Inside adj EMI...");
	System.out.println("Prn : "+prn);
	System.out.println("Install : "+install);
	System.out.println("Rate: "+rate);
	System.out.println("period: "+period);
	System.out.println("Prev: "+prev);
	System.out.println("Eff Dt: "+effective);
	System.out.println("Start : "+start);
	//Vector vec=new Vector(1,1);
	Map map_obj=null;
	List list_obj = new ArrayList();
	
	double cprate=rate,sumi=0.0,sumt=0.0,cin=0.0;
	
	StringTokenizer st=new StringTokenizer(effective,"/");
	int d=Integer.parseInt(st.nextToken());
	int m=Integer.parseInt(st.nextToken())-2;
	int y=Integer.parseInt(st.nextToken());
	GregorianCalendar g=new GregorianCalendar(y,m,d);	

	//double bal=Math.round(prn-(period*install));
	System.out.println("EMI");
	int i=0;
	String current=effective;

	for(i=0;i<period;i++)
	{
		map_obj=new TreeMap();
		System.out.println("Increment=============>");
		g.add(Calendar.MONTH,1);
		if(i!=0)
			try {
				current=Validations.checkDate(g.get(Calendar.DATE)+"/"+(g.get(Calendar.MONTH)+1)+"/"+g.get(Calendar.YEAR));
				System.out.println("Current============>"+current);
			} catch (DateFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		double interest=Math.round((prn*cprate*(Validations.dayCompare(prev,current)))/36500.00);
		System.out.println("Interest============>"+interest);
		prev=current;

		System.out.println("Prn====>"+prn+"Install"+install);
		if(prn<install) //At end if prn is less than installment means assing install to prn;
			{
				install=prn+interest;
			}

		System.out.println("Install======>"+install);
		if(i==(period-1))
		{
			cin=(interest+prn);			
			install=cin;
		}
		prn=prn-(install-interest);
		cin=(install-interest);
		System.out.println("Prn====>"+prn+"Cin"+cin+"Install"+install+"Prn"+prn+"Interest"+interest);

		if(cin<0 || install<0)
			break;

		
		{
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("the value of cin:"+cin);
			System.out.println("the value of interest:"+interest);
			System.out.println("the value of install:"+install);
			System.out.println("the value of prn:"+prn);
			System.out.println("the value of cin:"+cin);
			System.out.println("********************************************");
			
		}

		/*Object data[]=new Object[6];
		data[0]=String.valueOf(start++);
		data[1]=current;
		data[2]=String.valueOf(cin);
		data[3]=String.valueOf(interest);
		data[4]=String.valueOf(install);
		data[5]=String.valueOf(prn);	
		vec.addElement(data);*/
		System.out.println("ID====>"+start);
		System.out.println("PrincipalPaid"+cin);
		map_obj.put("id",i+1);
		map_obj.put("LoanTrandate",current);
		map_obj.put("PrincipalPaid",cin);
		map_obj.put("IntPaid",interest);
		map_obj.put("TranAmt",install);
		map_obj.put("LoanBal",prn);
		list_obj.add(map_obj);
		
		
		
		if(prn<=0)
			break;

		sumi+=interest;
		sumt=sumt+(prn);

	}
	 System.out.println("Vector Size"+list_obj.size());
	/*Object data[]=new Object[6];		
	data[0]="";
	data[1]="";
	data[2]="";
	data[3]=String.valueOf(sumi);
	data[4]=String.valueOf(sumt);
	data[5]="";	
	vec.addElement(data);*/ // Code added by Murugesh on 17/1/2006
	/*map_obj.put("id",start);
	map_obj.put("LoanTrandate",current);
	map_obj.put("PrincipalPaid",cin);
	map_obj.put("IntPaid",sumi);
	map_obj.put("TranAmt",sumt);
	map_obj.put("LoanBal",prn);
	list_obj.add(map_obj);*/

	
	
	System.out.println("End of adj EMI..");
	return list_obj;
}

}
