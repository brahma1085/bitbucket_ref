package general;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.ejb.CreateException;
import javax.naming.NamingException;

import masterObject.general.DoubleFormat;

//import com.scb.designPatterns.*;

import client.HomeFactory;
/*import client.MainScreen;*/

import commonServer.CommonHome;
import commonServer.CommonLocal;
import commonServer.CommonRemote;

import exceptions.DateFormatException;
import exceptions.InterestCalculation;

public class Validations
{
	public static String DateAdd(String date1,int no_months)
	{
		int dd=0,mm=0,yy=0;
		
		int dd1=0;
		int a1[]={31,28,31,30,31,30,31,31,30,31,30,31};
		int a2[]={31,29,31,30,31,30,31,31,30,31,30,31};
		StringTokenizer st=new StringTokenizer(date1,"/");
		dd=Integer.parseInt(st.nextToken());
		mm=Integer.parseInt(st.nextToken());
		yy=Integer.parseInt(st.nextToken());
		
		for(int i=0;i<no_months;i++)
		{
		if(mm==12)
		{
			mm=1;
			yy=yy+1;
		}
		else
			mm=mm+1;
		}
	
		if((yy%4==0)||(yy%100==0) &&(yy%400==0))
			dd1=a2[mm-1];
		else
			dd1=a1[mm-1];
			
		if(dd1<=dd)	
		dd=dd1;
		return (convertDMY(yy+"-"+mm+"-"+dd));
	}
	// fun added by SS 23/03/06
	public static int checkvalid(String date)
	{
		int dd=0,mm=0,yy=0;
		int dd1=0;
		int a1[]={31,28,31,30,31,30,31,31,30,31,30,31};
		int a2[]={31,29,31,30,31,30,31,31,30,31,30,31};
	
		StringTokenizer st;
		int d1,d2,m1,m2,y1,y2;
		
        st = new StringTokenizer(date,"/");
		d1 = Integer.parseInt(st.nextToken());
		m1 = Integer.parseInt(st.nextToken());
		y1 = Integer.parseInt(st.nextToken());
		
		String str1=String.valueOf(d1);
		String str2=String.valueOf(m1);
		String str3=String.valueOf(y1);
		
		if( str1.length() < 2 || str1.length() >2 || str2.length() < 2 || str2.length() >2 ||str3.length() < 4 || str3.length() >4)
			if( str1.length() < 2 || str1.length() >2 || str2.length() < 2 || str2.length() >2 ||str3.length() < 4 || str3.length() >4)
		{
			/*System.out.println(" string lentgh"+str1.length());
			System.out.println(" string lentgh"+str2.length());
			System.out.println(" string lentgh"+str3.length());*/
			return -1;
		}
		if(m1==4 ||m1==6 || m1==9 || m1==11 || m1==2 )
		{
			if(d1>30)
			{
				//JOptionPane.showMessageDialog(this,"please check date");
				return -1;
			}
		}
		if(m1==2) 
		{
			if(((y1%4==0) || (y1%100==0))&& (y1%4)==0)
			{
			if(d1>29)
				return -1;
			}
			else if(d1>28)
				return -1;
		}
		if(d1>31)
				return -1;
		

		return 1;
	}

	public static int validDate(String d,String sysd)
	{
		StringTokenizer st;
		int d1,d2,m1,m2,y1,y2;
        st = new StringTokenizer(d,"/");
		d1 = Integer.parseInt(st.nextToken());
		m1 = Integer.parseInt(st.nextToken());
		y1 = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(sysd,"/");
		d2 = Integer.parseInt(st.nextToken());
		m2 = Integer.parseInt(st.nextToken());
		y2 = Integer.parseInt(st.nextToken());
		
		if(y1>y2)
		return 1;
		else if(y1==y2 && m1>m2)
		return 1;
		else if(y1==y2 && m1==m2)
		{
			if(d1>d2)
			    return 1;
			return -1;
		}
		else return -1;

	}
	
	public static int checkDateValid(String d,String sysd)
	{
		StringTokenizer st;
		int d1,d2,m1,m2,y1,y2;
        st = new StringTokenizer(d,"/");
		d1 = Integer.parseInt(st.nextToken());
		m1 = Integer.parseInt(st.nextToken());
		y1 = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(sysd,"/");
		d2 = Integer.parseInt(st.nextToken());
		m2 = Integer.parseInt(st.nextToken());
		y2 = Integer.parseInt(st.nextToken());
		
		if(y1>y2)
		return 1;
		else if(y1==y2 && m1>m2)
		return 1;
		else if(y1==y2 && m1==m2)
		{
			if(d1>=d2)
			    return 1;
			return -1;
		}
		else return -1;

	}
	
	/*public static String addNoOfMonths(String date,int no_of_months) throws DateFormatException
	{

		StringTokenizer st;
		int d1,m1,y1,new_month=0;
		st = new StringTokenizer(date,"/");
		d1 = Integer.parseInt(st.nextToken());
		m1 = Integer.parseInt(st.nextToken());
		y1 = Integer.parseInt(st.nextToken());
		new_month = m1;
		while(no_of_months > 0)
		{
			if(new_month%12 == 0)
			{
				new_month = 1;
				y1++;
			}
			else
				new_month++;
			--no_of_months;
		}
		return checkDate(String.valueOf(d1+"/"+new_month+"/"+y1));
	}*/
	
	public static String addNoOfMonths(String date,int no_of_months) throws DateFormatException
	{	
		GregorianCalendar gbc=null;
		try{
			StringTokenizer st;
			int day,month,year;
			st = new StringTokenizer(date,"/");
		    
			day = Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken())-1;
			year = Integer.parseInt(st.nextToken());
			
			gbc = new GregorianCalendar(year,month,day);
			gbc.add(Calendar.MONTH,no_of_months);
			
		}catch(Exception exe){exe.printStackTrace();}
		
		return (Validations.checkDate(gbc.get(Calendar.DATE)+"/"+(gbc.get(Calendar.MONTH)+1)+"/"+gbc.get(Calendar.YEAR)));
	}
	public static String convertDMY1(String str)
	{
		StringTokenizer st=new StringTokenizer(str,"/");
		String yy=st.nextToken();
		String mm=st.nextToken();
		String dd=st.nextToken();
		
		return (((dd.length()==1)?("0"+dd):dd)+"/"+((mm.length()==1)?("0"+mm):mm)+"/"+yy);
	}

	public static String convertDMY(String str)
	{
		StringTokenizer st=new StringTokenizer(str,"-");
		String yy=st.nextToken();
		String mm=st.nextToken();
		String dd=st.nextToken();
		
		return (((dd.length()==1)?("0"+dd):dd)+"/"+((mm.length()==1)?("0"+mm):mm)+"/"+yy);
	}

	public static boolean checkNum(String s)
	{
		for(int i=0;i<s.length();i++)
			if(!((s.charAt(i)>='0' && s.charAt(i)<='9')|| s.charAt(i)=='.'))
				return false;

		return true;
	}
	
	public static boolean checkAlpha(String s)
	{
		if(s.trim().length()>0)
			return true;
		return false;

	}

	public static int dayCompare(String date1,String date2)
	{

	int a1[]={31,28,31,30,31,30,31,31,30,31,30,31};
	int a2[]={31,29,31,30,31,30,31,31,30,31,30,31};
	int d1,d2,m1,m2,y1,y2,days=0;
	try{

	StringTokenizer st;

        st = new StringTokenizer(date1,"/");
	d1 = Integer.parseInt(st.nextToken());
	m1 = Integer.parseInt(st.nextToken());
	y1 = Integer.parseInt(st.nextToken());

	st = new StringTokenizer(date2,"/");
	d2 = Integer.parseInt(st.nextToken());
	m2 = Integer.parseInt(st.nextToken());
	y2 = Integer.parseInt(st.nextToken());


	if(y2>y1)
	{

		for(int j=y1+1;j<=y2-1;j++)
		{
			//if(j%4==0)	
		if(((j%4==0) || (j%100==0))&& (j%4)==0)
	     {
			for(int i=0;i<12;i++)
			{
				days+=a2[i];
			//	System.out.println("days le="+days);
			}
	     }
		else
		 {
			
			for(int i=0;i<12;i++)
				days+=a1[i];
			//System.out.println("days odd+"+days);
		 }
		}

		for(int i=m1;i<12;i++)
		{
			//if(y1%4==0)
			if(((y1%4==0) || (y1%100==0))&& (y1%4)==0)
			  days+=a2[i];
			else
			  days+=a1[i];
		}


		for(int i=0;i<(m2-1);i++)
		{
			//if(y2%4==0)
			if(((y2%4==0) || (y2%100==0))&& (y2%4)==0)
			  days+=a2[i];
			else
			  days+=a1[i];
		}

		//if(y1%4==0)
		if(((y1%4==0) || (y1%100==0))&& (y1%4)==0)
			days=days+(a2[m1-1]-d1);
			//days=days+(a1[m1-1]-d1);
		else
			days=days+(a1[m1-1]-d1);
			//days=days+(a2[m1-1]-d1);

		days=days+d2;
	}
	else if(y2==y1)
	{
		if(m2>m1)
		{
		for(int i=m1;i<(m2-1);i++)
		{
			if(y1%4==0)
			  days+=a2[i];
			else
			  days+=a1[i];
		}

		if(y1%4==0)
			days=days+(a2[m1-1]-d1);
		else
			days=days+(a1[m1-1]-d1);

		days=days+d2;
		}
		else if(m1==m2)
		{
			days=d2-d1;

		}
		else
		  return -1;


	}
	else
		return -1;
	}catch(Exception ex){}


	return days;
	}
	
	public static String checkDate12(String date) throws DateFormatException
	{
	    try 
	    {
	    	
		String fdate="";
		StringTokenizer stfdate=new StringTokenizer(date,"/");
		String dd=stfdate.nextToken();
		String mm=stfdate.nextToken();
		String yyyy=stfdate.nextToken();
		int mmi=Integer.parseInt(mm);

		if(Integer.parseInt(dd)==0 || dd.length()>2 || (Integer.parseInt(dd))>((mmi==2?((Integer.parseInt(yyyy)%4)==0?29:28):((mmi==1 ||mmi==3||mmi==5 ||mmi==7 ||mmi==8 ||mmi==10||mmi==12)?31:30))))
		{
			throw new DateFormatException("Invalid Date Format");
		}
		else if(mm.length()>2 || Integer.parseInt(mm)>12 || Integer.parseInt(mm)==0)
		{
			throw new DateFormatException("Invalid Month Format");
		}
		else if((yyyy.length()==1 || yyyy.length()==3 ||yyyy.length()>=5 || Integer.parseInt(yyyy)==0) && !(yyyy.equalsIgnoreCase("10000")))
		{
			throw new DateFormatException("Invalid Year Format");
		}		
		else
		{
			if(yyyy.length()==2)
			{
			  Calendar c=Calendar.getInstance();
			  int y=c.get(Calendar.YEAR);
			  y=y/100;
			  yyyy=String.valueOf(y)+yyyy;

			}
			if(dd.length()==1)
				dd="0"+dd;

			if(mm.length()==1)
				mm="0"+mm;

			fdate=dd+"/"+mm+"/"+yyyy;
			
			
			return fdate;
		}	
	    }catch(NoSuchElementException e) {
	        throw new DateFormatException("Date Problem"); 
	    }catch(NumberFormatException nf ) {
	    	throw new DateFormatException("Invalid Date Format"); 
	    }
	}

	public static String checkDate(String date) throws DateFormatException
	{
	    try {
	    	
		String fdate="";
		StringTokenizer stfdate=new StringTokenizer(date,"/");
		String dd=stfdate.nextToken();
		String mm=stfdate.nextToken();
		String yyyy=stfdate.nextToken();
		int mmi=Integer.parseInt(mm);

		if(Integer.parseInt(dd)==0 || dd.length()>2 || (Integer.parseInt(dd))>((mmi==2?((Integer.parseInt(yyyy)%4)==0?29:28):((mmi==1 ||mmi==3||mmi==5 ||mmi==7 ||mmi==8 ||mmi==10||mmi==12)?31:30))))
		{
			throw new DateFormatException("Invalid Date Format");
		}
		else if(mm.length()>2 || Integer.parseInt(mm)>12 || Integer.parseInt(mm)==0)
		{
			throw new DateFormatException("Invalid Month Format");
		}
		else if((yyyy.length()==1 || yyyy.length()==3 ||yyyy.length()>=5 || Integer.parseInt(yyyy)==0) && !(yyyy.equalsIgnoreCase("10000")))
		{
			throw new DateFormatException("Invalid Year Format");
		}		
		else
		{
			if(yyyy.length()==2)
			{
			  Calendar c=Calendar.getInstance();
			  int y=c.get(Calendar.YEAR);
			  y=y/100;
			  yyyy=String.valueOf(y)+yyyy;

			}
			if(dd.length()==1)
				dd="0"+dd;

			if(mm.length()==1)
				mm="0"+mm;

			fdate=dd+"/"+mm+"/"+yyyy;
			
			//System.out.println("In validations checkDate method==> "+fdate);
			return fdate;
		}	
	    }catch(NoSuchElementException e) {
	        throw new DateFormatException("Date Problem"); 
	    }catch(NumberFormatException nf ) {
	    	throw new DateFormatException("Invalid Date Format"); 
	    }
	}

	public static double rdInterestCalculation(double amt,int instl,double r,String opdate,String cldate)
	{
		//System.out.println("cldate == "+cldate);
	    double intr=0.0,p1=0.0,p2;
		int qrts,mths;
		String d1=opdate;
		String d2=null;
		int i;

		StringTokenizer st=new StringTokenizer(d1,"/");
		String day=st.nextToken();
		int month=Integer.parseInt(st.nextToken());
		int year=Integer.parseInt(st.nextToken());
		qrts=instl/3;
		mths=instl%3;

		for(i=1;i<=qrts;i++)
		{
			int loop=3;
			intr=0.0;
			while(loop>=1)
			{
				++month;
				if((month)>12){month%=12;year+=1;}
				d2=day+"/"+month+"/"+year;
				int noofdays=dayCompare(d1,d2);
				p1+=amt;
				intr+=(p1*noofdays*r/36500);
				d1=d2;
				loop--;
			}
			p1+=intr;

		}
		intr=0.0;
		p2=0.0;
		for(i=1;i<=mths;i++)
		{
			p2+=amt;
			++month;
			if((month)>12){month%=12;year+=1;}
			d2=day+"/"+month+"/"+year;
			int noofdays=dayCompare(d1,d2);
			intr+=(p2*r*noofdays/36500);
			d1=d2;
		}
		p1=p1+p2+intr;
		System.out.println("--------in validationsss----"+p1);
		return Math.round(p1);
	}
	
	// Function changed by Murugesh on 10/07/2006
	/*public static String addDays(String date,int interval) throws DateFormatException
	{
		
	    int a1[]={31,28,31,30,31,30,31,31,30,31,30,31};
	    int a2[]={31,29,31,30,31,30,31,31,30,31,30,31};
	    
	    StringTokenizer st;
	    int d1,y1;
	    st = new StringTokenizer(date,"/");
    
	    d1 = Integer.parseInt(st.nextToken());
	    //System.out.println("d1 value="+d1);
	    int m1 = Integer.parseInt(st.nextToken());
	    //System.out.println("m1 value="+m1);
	    y1 = Integer.parseInt(st.nextToken());
	    //System.out.println("y1 value="+y1);

	    if(interval>0)
	    {
	        d1=d1+interval;
	        //System.out.println("d1 one="+d1);
	        if(y1%4==0)
	        {
	            while(d1>a2[m1-1])
	            {
	                d1=d1-a2[m1-1];
	                //System.out.println("d1 two="+d1);
	                if(m1<12)
	                    m1++;
	                else
	                {
	                    y1++;
	                    m1=1;
	                }
	                
	            }
	        }
	        else
	        {
	        	//System.out.println("i am in else");
	            while(d1>a1[m1-1])
	            {
	                d1=d1-a1[m1-1];
	                //System.out.println("d1 three="+d1);
	                if(m1<12)
	                    m1++;
	                else
	                {
	                    y1++;
	                    m1=1;
	                }
	            }
	        }
	    }
	    else if(interval<0)
	    {
	        d1=d1+interval;
	        //System.out.println("d1 four="+d1);
	        if(d1<=0)
	        {
	        	//System.out.println("d1 less than 0");
	            if(y1%4==0)
	            {
	                while(d1<=0)
	                {
	                    if(d1==0 && m1==1)
	                    {
	                    	//System.out.println("d1 equals 0");
	                        y1--;
	                        d1+=(y1%4==0?a1[11]:a2[11]);
	                        //System.out.println("d1 five="+d1);
	                        
	                    }
	                    else if(m1==1)
	                    {
	                        d1+=a1[m1-1];
	                        //System.out.println("d1 six="+d1);
	                        y1--;
	                    }
	                    else
	                    {
	                        d1+=a2[m1-2];
	                        //System.out.println("d1 seven="+d1);
	                        if(m1<=12)
	                            m1--;
	                        else
	                        {
	                            y1--;
	                        }
	                    }
	                }
	            }
	            else
	            {
	                while(d1<=0)
	                {
	                    if(d1==0 && m1==1)
	                    {
	                        y1--;
	                        d1+=(y1%4==0?a1[11]:a2[11]);
	                        //System.out.println("d1 eight="+d1);
	                        
	                    }
	                    else if(m1==1)
	                    {
	                        d1+=a1[m1-1];
	                        //System.out.println("d1 nine="+d1);
	                        y1--;
	                    }
	                    
	                    else
	                    {
	                        d1+=a1[m1-2];
	                        //System.out.println("d1 ten="+d1);
	                        if(m1<=12)
	                            m1--;
	                        else
	                        {
	                            y1--;
	                        }
	                    }
	                    
	                }
	            }
	        }
	        
	    }
	    return(Validations.checkDate(d1+"/"+m1+"/"+y1));
	}*/
	
	// Function added by Murugesh on 10/07/2006
	public static String addDays(String date,int interval) throws DateFormatException
	{	
		GregorianCalendar gbc=null;
		try{
			System.out.println(" Date in validations is==> "+date);
			System.out.println(" interval in validations is===> "+interval);
			StringTokenizer st;
			int day,month,year;
			st = new StringTokenizer(date,"/");
		    
			day = Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken())-1;
			//month = Integer.parseInt(st.nextToken());
			year = Integer.parseInt(st.nextToken());
			System.out.println("Date is ----->"+year+" "+month+" "+day);
			gbc = new GregorianCalendar(year,month,day);
			gbc.add(Calendar.DATE,interval);
			System.out.println("dATE---> "+gbc.get(Calendar.DATE));
			System.out.println(" mOnth---> "+(gbc.get(Calendar.MONTH)+1));
			System.out.println("yEAR---> "+gbc.get(Calendar.YEAR));
			System.out.println(" Date Inside validations===> "+Validations.checkDate(gbc.get(Calendar.DATE)+"/"+(gbc.get(Calendar.MONTH)+1)+"/"+gbc.get(Calendar.YEAR))); 
		}catch(Exception exe){exe.printStackTrace();}
		
		
		return (Validations.checkDate(gbc.get(Calendar.DATE)+"/"+(gbc.get(Calendar.MONTH)+1)+"/"+gbc.get(Calendar.YEAR)));
	}
	public static String addDays1(String date,int interval) throws DateFormatException
	{	
		GregorianCalendar gbc=null;
		try{
			System.out.println(" Date in validations is==> "+date);
			System.out.println(" interval in validations is===> "+interval);
			StringTokenizer st;
			int day,month,year;
			st = new StringTokenizer(date,"-");
		    
			day = Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken())-1;
			year = Integer.parseInt(st.nextToken());
			
			gbc = new GregorianCalendar(year,month,day);
			gbc.add(Calendar.DATE,interval);

		}catch(Exception exe){exe.printStackTrace();}
		System.out.println(" Date Inside validations===> "+Validations.checkDate(gbc.get(Calendar.DATE)+"/"+(gbc.get(Calendar.MONTH)+1)+"/"+gbc.get(Calendar.YEAR)));
		
		return (Validations.checkDate(gbc.get(Calendar.DATE)+"/"+(gbc.get(Calendar.MONTH)+1)+"/"+gbc.get(Calendar.YEAR)));
	}
 

	public static String nextPayDate(String intfreq,String date,String matdate,CommonLocal commonRemote) throws NamingException, RemoteException, CreateException
	{
	    /*CommonHome commonHome = (CommonHome) HomeFactory.getFactory().lookUpHome("COMMONWEB");
	    CommonRemote commonRemote = commonHome.create();
		*/
		String nextpaydate=null;
		if(intfreq.equals("M"))
		    nextpaydate=commonRemote.getFutureMonthDate(date,1);
		else if(intfreq.equals("Q")) {
		    nextpaydate=commonRemote.getFutureMonthDate(date,3);		    
		}
		else if(intfreq.equals("H"))
		    nextpaydate=commonRemote.getFutureMonthDate(date,6);
		else if(intfreq.equals("Y"))
		    nextpaydate=commonRemote.getFutureMonthDate(date,12);
		else
			nextpaydate=matdate;
		if(commonRemote.getDaysFromTwoDate(date, date) > 0 ) 
		    nextpaydate=matdate;
		return(nextpaydate);
	}

	/*public static String getReportHeader(String str,Heading head,int column)
	{
		String rephead="";

		rephead=makeCenter(head.getBankName(),column)+"\n";
		rephead+=makeCenter(head.getBankLocation(),column)+"\n";
		rephead+=makeCenter(str,column)+"\n";
		return rephead;

	}*/


	public static String makeCenter(String name,int length)
	{
		int pos=0;
		if(name.length()<length)
		{
			pos=length-name.length();
			pos=pos/2;
		}
		return(adjust(name,(pos+name.length()),2));
	}
	public static String adjust(String str,int n,int type)
	{
		String newstr="";
		int pos=0;
		if(str.length()<n)
		{
			pos=n-str.length();
			pos=pos/2;
		}

		if(type==2)
			n=pos+str.length();

		if(str.length()<n)
		{
			for(int i=str.length();i<=n;i++)
				newstr=newstr+" ";
			if(type==0)
				newstr=str+newstr;
			if(type==1)
				newstr=newstr+str;
			if(type==2)
				newstr=newstr+str+newstr;
		}
		else
			newstr=str;

		return newstr;
	}

	public static String getLine(String str,int n)
	{
		String newstr="";
		for(int i=0;i<n;i++)
			newstr+=str;
		return(newstr);
	}

	public static String convertYMD(String str)
	{
		System.out.println("str in Validations---> "+str);
		StringTokenizer st=new StringTokenizer(str,"/");
		String dd=st.nextToken();
		String mm=st.nextToken();
		String yy=st.nextToken();
		return (yy+"-"+mm+"-"+dd);
	}

	
	public 	static double rdIntReCal(double amt,double rate,String opdate,String closedate,double arr[],String trndate[],String mat_date,String  next_pay_date) throws NamingException, RemoteException, CreateException, InterestCalculation
	{ 
		String current_quater_date = opdate;
		String current_quater_date3=null;
		String previous_quater = null;
		int quater = 0;
		double cum_amt = 0;
		double dep_amt = 0;
		double interest = 0;
		double  rd_bal =0.0;
		String[] temp_date = null;
		double[] temp_amt = null;
		//added by geetha 
		double cum_penal=0.0;
		double quarter_amount=0.0;
		
        System.out.println(" +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 2222");
        System.out.println("inside rdIntReCal222222--------------");
        
        System.out.println(" the amt is " + amt);
        
        System.out.println(" the rate is " + rate);
        
        System.out.println(" the open date is " + opdate);
        
        System.out.println("the next pay date"+ next_pay_date );
        
        System.out.println(" the close date is " + closedate);
        
        System.out.println(" maturity date " + mat_date);
            
        for (int k = 0 ; k < trndate.length; k++) {
        	System.out.println(" the " + k + " value of trndate is " + trndate[k] + " amt " + arr[k]);
        }
        
        
        System.out.println(" +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        
		try{
			System.out.println("current_quater_date "+current_quater_date);
			//added by geetha on 2/01/08
			
			System.out.println("closedate.. geetha" + closedate);
			
			current_quater_date3 = addNoOfMonths(current_quater_date, 3);
			
			System.out.println("current_quater_date after 3 "+current_quater_date);
			
			
			//this condition is wen the customer try to close the acccount before 1 quarter is completed...
			
			if((Validations.dayCompare(current_quater_date3,closedate)<= 0)){
				//	if(Validations.dayCompare(current_quater_date, closedate) >= 0 &&  next_pay_date >closedate){
				
				System.out.println("no of days"+Validations.dayCompare(current_quater_date, closedate));
				cum_penal += (Validations.dayCompare(current_quater_date, closedate)*rate* amt /36500);
				System.out.println("cum interest is "+cum_penal);
				cum_amt = cum_penal + amt ;
			}
			
			
			
			
			//added by geetha
			else{
				current_quater_date = addNoOfMonths(current_quater_date, 3);
				
				
				String temp_currentdate=current_quater_date;
				
				Hashtable hash=new Hashtable();
				
				TreeMap tree_amount=new TreeMap();
				TreeMap tree_int_date=new TreeMap();
				TreeMap tree_index=new TreeMap();
				TreeMap tree_tot_Amount=new TreeMap();
				
				double amount=0.0,temp_amount=0.0;
				double temp_interest=0.0;
				int index=0;
				
				
				for(int m=0;m<trndate.length;m++) {
					
					System.out.println("--------------------------------");
					System.out.println("trndate>> "+trndate[m]);
					System.out.println("current_quater_date>> "+temp_currentdate);
					System.out.println("current_quater_date3>> "+current_quater_date3);
					System.out.println("--------------------------------");
					System.out.println("--------------------------------");
					System.out.println("Validations.dayCompare(temp_currentdate,closedate)>>"+Validations.dayCompare(temp_currentdate,closedate));
					
					/*if((Validations.dayCompare(trndate[m],mat_date)<= 0)){
						continue;
					}*/
					if(Validations.dayCompare(trndate[m], temp_currentdate)>0) {
						
						tree_int_date.put(trndate[m], temp_currentdate);
						
						amount+=arr[m];
						
						interest = (Validations.dayCompare(trndate[m], temp_currentdate)*rate*arr[m]/36500);
						temp_interest+=interest;
						tree_amount.put(trndate[m],interest);
						tree_tot_Amount.put(trndate[m], amount);
						tree_index.put(index, trndate[m]);
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
						System.out.println("Added trndate["+m+"]=="+trndate[m]+"\t current_quarter_date=="+temp_currentdate);
						System.out.println("days "+Validations.dayCompare(trndate[m], temp_currentdate)+"\t"+interest);
						System.out.println("temp_interest== "+temp_interest);
						System.out.println("amount== "+amount);
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
						index++;
					}
					if(Validations.dayCompare(trndate[m], temp_currentdate)<0) {
						if(Validations.dayCompare(temp_currentdate,current_quater_date3)==0) {
							
							System.out.println("+++++++++++++++");
							System.out.println("temp_currentdate >> "+temp_currentdate);
							System.out.println("mat_date >> "+mat_date);
							temp_currentdate = addNoOfMonths(temp_currentdate, 3);
							if(Validations.dayCompare(temp_currentdate,mat_date)<0) {
								temp_currentdate = mat_date;
							}
							
								
							
							
							System.out.println("temp_interest== "+temp_interest);
							
							amount+=Math.round(temp_interest);
							
							System.out.println("added current_quater_date3 >> "+current_quater_date3+"temp_currentdate >>"+temp_currentdate);
							
							interest = (Validations.dayCompare(current_quater_date3, temp_currentdate)*rate*amount/36500);
							temp_interest=interest;
							System.out.println("days "+Validations.dayCompare(current_quater_date3, temp_currentdate)+"\t"+interest);
							System.out.println("amount=="+amount);
							System.out.println("temp_interest=="+temp_interest);
							
							tree_amount.put(current_quater_date3,interest);
							tree_int_date.put(current_quater_date3,temp_currentdate);
							tree_index.put(index, current_quater_date3);
							tree_tot_Amount.put(current_quater_date3, amount);
							
							current_quater_date3=temp_currentdate;
							index++;
							
							if(Validations.dayCompare(trndate[m],closedate)!=0) {
								amount+=arr[m];
								interest = (Validations.dayCompare(trndate[m],temp_currentdate)*rate*arr[m]/36500);
								temp_interest+=interest;
								
								System.out.println("temp_interest== "+temp_interest);
								System.out.println("days "+Validations.dayCompare(trndate[m],temp_currentdate)+"\t"+interest);
								
								System.out.println("temp_interest=="+temp_interest);
								
								tree_amount.put(trndate[m],interest);
								tree_int_date.put(trndate[m],temp_currentdate);
								tree_index.put(index, trndate[m]);
								tree_tot_Amount.put(trndate[m], amount);
								index++;
							}
							System.out.println("added TRN_DATE >> "+trndate[m]+"temp_currentdate >>"+temp_currentdate);
							System.out.println("++++++++++++++++");
						}
					}
				}
				System.out.println("final amount=="+amount);
				System.out.println("temp_interest=="+temp_interest);
				
				if(Validations.dayCompare(temp_currentdate,closedate)>0) {
					while(Validations.dayCompare(temp_currentdate, closedate)>0) {
					
						temp_currentdate = addNoOfMonths(temp_currentdate, 3);
						/*if(Validations.dayCompare(temp_currentdate,mat_date)<0) {
							temp_currentdate = mat_date;
						}*/
						System.out.println("current_quater_date "+current_quater_date3+"   temp_currentdate "+temp_currentdate+" closedate >>"+closedate);
						amount+=temp_interest;
						System.out.println(Validations.dayCompare(temp_currentdate,closedate));
						if(Validations.dayCompare(temp_currentdate,mat_date)<0) {
							System.out.println("current_quater_date "+current_quater_date3+"   temp_currentdate "+temp_currentdate+" closedate >> "+closedate);
							interest = (Validations.dayCompare(current_quater_date3,mat_date)*rate*amount/36500);
						//	interest=0;
						}
						else
							interest = (Validations.dayCompare(current_quater_date3,temp_currentdate)*rate*amount/36500);
						temp_interest=interest;
						System.out.println("interest >> "+interest);
						tree_int_date.put(current_quater_date3, temp_currentdate);
						tree_index.put(index, current_quater_date3);
						tree_amount.put(current_quater_date3,interest);
						/*interest = (Validations.dayCompare(current_quater_date3, temp_currentdate)*rate*temp_amt[m]/36500);*/
						/*hash_amount.put(key, value)*/
						current_quater_date3=temp_currentdate;
						index++;
					}
				}
				
				System.out.println("quarter_amount--->"+quarter_amount);
				System.out.println("--------------------------------------------");
				
				for(int z=0;z<tree_int_date.size();z++) {
					String key=tree_index.get(z).toString().trim();
					quarter_amount+=DoubleFormat.doublePrecision(Double.parseDouble(tree_amount.get(key).toString().trim()),2);
					System.out.println(key+"\t"+tree_int_date.get(key)+"\t"+tree_amount.get(key));
					
				}
				System.out.println("final quarter_amount -->"+quarter_amount);
				System.out.println("--------------------------------------------");
				
				/*Set keySet=tree_int_date.keySet();
				Iterator iter=keySet.iterator();
				while(iter.hasNext()) {
					String key=iter.next().toString().trim();
					String value=tree_int_date.get(key).toString().trim();
					System.out.println(key+"\t"+value+"\t"+tree_amount.get(key));
				}*/
					
			/*	while(e.hasMoreElements()) {
					String key=e.nextElement().toString().trim();
					String value=hash.get(key).toString().trim();
					System.out.println(key+"\t"+value+"\t"+hash_amount.get(Validations.dayCompare(key,value)));
					
				}
				
*/				
			//commented by geetha since new logic is implemented 	
			/*	while (Validations.dayCompare(current_quater_date, closedate) >= 0) {
				
				temp_date = new String[3];
				temp_amt = new double[3];
				
				
				for(int i = (quater*3), inst = 0; i < trndate.length && inst<3; i++) {
					//System.out.println(" the diff between " + current_quater_date + " and " + trndate[i] + " is " + Validations.dayCompare(current_quater_date, trndate[i]));
					if(Validations.dayCompare(current_quater_date, trndate[i]) <= 0) {
						temp_date[inst] =  trndate[i];
						temp_amt[inst] = arr[i];
					}
					inst++;
				}
				
				if(quater > 0) {
					cum_amt += (Validations.dayCompare(previous_quater, current_quater_date)*rate*cum_amt/36500);
				}
				
				for(int i = 0 ; i < temp_date.length; i++) {
					//System.out.println(" the "+ (quater + 1) +" qauter"+ current_quater_date + " values are date: " + temp_date[i]+ " amt:" + temp_amt[i]);
					if(temp_date[i] != null ) {
							interest = (Validations.dayCompare(temp_date[i], current_quater_date)*rate*temp_amt[i]/36500);
							System.out.println("interest gee"   + interest);
					} else {
						interest = 0;
					}
					
					System.out.println(" the "+ (quater + 1) +" quarter"+ current_quater_date + " values are date: " + temp_date[i]+ " amt:" + temp_amt[i]+ " the interst is " + Math.round(interest));
					
					
					cum_amt += interest +  temp_amt[i];
					
					System.out.println("cum_amt is" +  cum_amt);
					
					
					dep_amt += temp_amt[i];
					
					System.out.println("cccc cum_amt"+ cum_amt);
					
				}
				
				System.out.println(" the " + (quater+1) + " int amt is " + Math.round(cum_amt - dep_amt));
				
				cum_amt = Math.round(cum_amt  );
				
				quater++;
				previous_quater = current_quater_date;
				current_quater_date = addNoOfMonths(current_quater_date, 3);
				
				if(Validations.dayCompare(current_quater_date, closedate) < 0) {
					current_quater_date = closedate;
				}
				
				if(previous_quater.equalsIgnoreCase(closedate)) {
					break;
				}
			}*/
		}	
			
			System.out.println(" the current_quater_date is " + current_quater_date);
								
			System.out.println(" ++++++++++++++++++++++++++++++++++++++===> "+quarter_amount);
		
		}
		catch(Exception e){e.printStackTrace();
		}
		  return Math.round(quarter_amount);
	}
	
	
	
	
	
	
	
	
	
	
	
	public 	static double rdIntReCal(double amt,double rate,String opdate,String closedate,double arr[],String trndate[]) throws NamingException, RemoteException, CreateException, InterestCalculation
	{ 
		int length = arr.length,qrts,months=0,t=0;
		int no_of_days=0;
		
		double intrest=0.0,nextamt=amt;
		String fromdate=opdate,todate=opdate,date=opdate;
		CommonRemote commonRemote = null;
		CommonHome commonhome=null;
		System.out.println("**********in validat*********"); 
		CommonHome commonHome = (CommonHome) HomeFactory.getFactory().lookUpHome("COMMONWEB");
	    commonRemote = commonHome.create();
		      
        System.out.println("**********in validat 2*********"); 
        months=noOfMonths(opdate,closedate);
        
        System.out.println("length==>"+length);
        System.out.println("months==>"+months);
        qrts=months/3;
        System.out.println("Qtrs==>"+qrts);
        
	try{
		for(int i=1;i<=qrts;i++)
		{
			int loop=3;
			todate=commonRemote.getFutureMonthDate(todate,3);
			System.out.println("todate==>"+todate);
			while(loop>=1)
		  	{
					System.out.println("t==>"+t);
					if(t==length-1)
						break;
					
		  			if(t<length){
		  				fromdate= trndate[t];
		  				date=trndate[t+1];
		  				nextamt = arr[t]; 
		  			}
		  			else
		  				date=closedate;
		  			System.out.println("date==>"+date);
		  			
		  			t++;
		  			
		  			no_of_days=commonRemote.getDaysFromTwoDate(date,todate);
		  			System.out.println("days==>"+no_of_days);
		  			if(no_of_days<=0)
		  			{
		  				no_of_days=commonRemote.getDaysFromTwoDate(fromdate,todate);
		  				System.out.println("days==>"+no_of_days);
		  				intrest+=Math.round(amt*no_of_days*rate/36500);
		  				//break;
		  				no_of_days=commonRemote.getDaysFromTwoDate(todate,date);
		  				System.out.println("days==>"+no_of_days);
		  				intrest+=Math.round(amt*no_of_days*rate/36500);
		  				
		  			}
		  			else{
		  				no_of_days=commonRemote.getDaysFromTwoDate(fromdate,date);
		  				System.out.println("amt==>"+amt);
		  				intrest+=Math.round(amt*no_of_days*rate/36500);
		  			}
		  			amt+=nextamt;
		  			loop--;
			}
		  	amt+=intrest;intrest=0.0;
		}
		while(t<length-1){
			System.out.println("t==>"+t);
			fromdate= trndate[t]; 
			if(t<length)
  				date=trndate[t+1];
			else
				date=closedate;
  			nextamt = arr[t]; 
  			t++;
  			todate=closedate;
  			System.out.println("todate==>"+todate);
  			no_of_days=commonRemote.getDaysFromTwoDate(date,todate);
  			System.out.println("days==>"+no_of_days);
  			if(no_of_days<=0)
  			{
  				no_of_days=commonRemote.getDaysFromTwoDate(fromdate,todate);
  				System.out.println("amt==>"+amt);
  				intrest+=Math.round(amt*no_of_days*rate/36500);
  				//break;
  				no_of_days=commonRemote.getDaysFromTwoDate(todate,date);
  				System.out.println("days==>"+no_of_days);
  				intrest+=Math.round(amt*no_of_days*rate/36500);
  			}
  			else{
  			no_of_days=commonRemote.getDaysFromTwoDate(fromdate,date);
  			System.out.println("amt==>"+amt);
  			intrest+=Math.round(amt*no_of_days*rate/36500);
  			}
  			if(t!=length-1)
  			amt+=nextamt;
  			fromdate=date;
  			//todate=date;
		}
		System.out.println("interest==>"+intrest);
	}
	catch(Exception e){e.printStackTrace();}
		return Math.round(amt+intrest);
	} 


	public static int rdMaturityDate(double amt,String depdate,String closedate,String cur_date ,double arr[],String trndate[]) throws NamingException, RemoteException, CreateException, InterestCalculation
	{
	    //System.out.println("closedate == "+closedate+"  cur_date == "+cur_date);
		String paydate=depdate;
		int excdays=0;
		double trn_amt=amt,inst_amt=amt;
		
		CommonRemote commonRemote = null;
		
		CommonHome commonHome = (CommonHome) HomeFactory.getFactory().lookUpHome("COMMONWEB");
        commonRemote  = commonHome.create();

		try{
		for(int i=1;i<trndate.length;i++)
		{

			StringTokenizer st=new StringTokenizer(paydate,"/");
			String d=st.nextToken();
			int m=Integer.parseInt(st.nextToken());
			int y =Integer.parseInt(st.nextToken());
			if(arr[i]==0)
				continue;
			if(m==12)
			{
				 y++;m=1;
			}
			else
				m++;

			paydate=d+"/"+m+"/"+y;
			inst_amt+=amt;
			int nodays=commonRemote.getDaysFromTwoDate(paydate,trndate[i]);

			if(nodays>10 && trn_amt<inst_amt)
				excdays=excdays+nodays;
			trn_amt+=arr[i];

		}
		}catch(Exception qw){
		    throw new InterestCalculation();
		}
		System.out.println("+++++++++++++excdays in Validations++++++++++===> "+excdays);
		return excdays;
	}

	public static int noOfMonths(String fromdate,String todate)
	{
		StringTokenizer fd,td;
		int m1,m2,y1,y2,d1,d2;

		fd=new StringTokenizer(fromdate,"/");
		d1 =Integer.parseInt(fd.nextToken());
		m1 =Integer.parseInt(fd.nextToken());
		y1=Integer.parseInt(fd.nextToken());

		td=new StringTokenizer(todate,"/");
		d2 =Integer.parseInt(td.nextToken());
		m2 =Integer.parseInt(td.nextToken());
		y2=Integer.parseInt(td.nextToken());
		int no_of_months=0;
		while(y1<y2)
		{
			no_of_months++;
			if(++m1==13){
				y1++;
				m1=1;
			}
		}
		while(true)
		{
			if(m1==m2)
				break;
			no_of_months++;
			m1++;
		}
		if(y2>y1)
		{
		while(y2>(y1+1))
		{
				for(int i=0;i<12;i++)
				{
					no_of_months++;
					//System.out.println("i amm in year");
				}
		     	y1++;
		     	//System.out.println("y1="+y1);
		}
			
		for(int i=m1;i<12;i++)
		{
			//System.out.println("i amm in m1");
			no_of_months++;
		}
		for(int i=0;i<(m2-1);i++)
		{
			
			//System.out.println("i amm in m2"+m2);
			no_of_months++;
		}
		}
		else if(y2==y1)
		{
			if(m2>m1)
			{
			for(int i=m1;i<(m2-1);i++)
			{
			no_of_months++;
			}
			}
		}

		System.out.println("testing111111111111111111111"+no_of_months);
			
		return no_of_months;
	}

	
	public static int getDefaultMonths(String from_date,String to_date)
	{
		GregorianCalendar gbc=null;
		int default_months=0;
		try{
			StringTokenizer st;
			int day,month,year;
			st = new StringTokenizer(from_date,"/");
		    
			day = Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken())-1;
			year = Integer.parseInt(st.nextToken());
			
			gbc = new GregorianCalendar(year,month,day);
			
			while(Validations.dayCompare(from_date,to_date)>0){
				default_months++;
				gbc.add(Calendar.MONTH,1);
				from_date=(gbc.get(Calendar.DATE)+"/"+(gbc.get(Calendar.MONTH)+1)+"/"+gbc.get(Calendar.YEAR));
			}
			
		}catch(Exception exe){exe.printStackTrace();}
		return default_months;
	}

	
	public static double reinvestmentCalc(double amt,String depdate,String matdate,double rate) throws NamingException, RemoteException, CreateException
	{
		//changed by sumanth to COMMONWEB as it was giving exception javax.naming.NamingException: (COMMONEJBWEB) Name Not found.. 
    	//CommonHome commonHome = (CommonHome) HomeFactory.getFactory().lookUpHome("COMMON");
		CommonHome commonHome = (CommonHome) HomeFactory.getFactory().lookUpHome("COMMONWEB");
		CommonRemote commonRemote = commonHome.create();
		
		System.out.println("&&&&&&&&&&&&&&&&inside reinvestment calc validations ");
		
    	String todate=depdate;
		String nextdate = commonRemote.getFutureMonthDate(todate,3);
		double pamt=amt;
		int no_of_days = 0;
		while(commonRemote.getDaysFromTwoDate(nextdate,matdate) > 0) {
		    no_of_days = commonRemote.getDaysFromTwoDate(todate, nextdate);
		    pamt += ((pamt*rate*no_of_days)/(36500));
		    todate = nextdate;
		    nextdate = commonRemote.getFutureMonthDate(todate,3);		    
		}
		no_of_days = commonRemote.getDaysFromTwoDate(todate, matdate);
	    pamt += ((pamt*rate*no_of_days)/(36500));	     
		return Math.round(pamt);
	}
	
	/*public static double reinvestmentCalc(double amt,String depdate,String matdate,double rate) throws NamingException, RemoteException, CreateException
	{
    	CommonHome commonHome = (CommonHome) HomeFactory.getFactory().lookUpHome("COMMONEJBWEB");
    	CommonRemote commonRemote = commonHome.create();
		
		System.out.println("&&&&&&&&&&&&&&&&inside reinvestment calc validations ");
		
    	String todate=depdate;
		String nextdate = commonRemote.getFutureMonthDate(todate,3);
		double pamt=amt;
		int no_of_days = 0;
		while(commonRemote.getDaysFromTwoDate(nextdate,matdate) > 0) {
		    no_of_days = commonRemote.getDaysFromTwoDate(todate, nextdate);
		    pamt += ((pamt*rate*no_of_days)/(36500));
		    todate = nextdate;
		    nextdate = commonRemote.getFutureMonthDate(todate,3);		    
		}
		no_of_days = commonRemote.getDaysFromTwoDate(todate, matdate);
	    pamt += ((pamt*rate*no_of_days)/(36500));	     
		return Math.round(pamt);
	}*/
	
	
	public static String changeFloat(double d)
	{
		String st=String.valueOf(d);
		int a=st.lastIndexOf(".");
		if(a!=-1)
		{
			int pos=st.length()-a;			
			if(pos>3)
			{
				if(Integer.parseInt(st.substring((a+3),(a+4)))>=5)
				{
				       double d1=Double.parseDouble(st)+0.01;
					st=String.valueOf(d1);
					st=st.substring(0,(a+3));
				}
				else
				{
					st=st.substring(0,(a+2));					
					st=st+"0";
				}
			}
			pos=st.length()-a;			
			if(pos==2)
				st=st+"0";

		}
		return st;
	}
	
		
	public static boolean checkInteger(String s) {
		
		for(int i=0;i<s.length();i++)
			if(!((s.charAt(i)>='0' && s.charAt(i)<='9')))
				return false;
		return true;		
	}

		public static boolean checkDecimal(String s) {
		for(int i=0;i<s.length();i++)
			if(!((s.charAt(i)>='0' && s.charAt(i)<='9')|| s.charAt(i)=='.'))
				return false;
		return true;
	}
		
		public static String checkTime(String time) {
			StringTokenizer st = new StringTokenizer(time,":");
			String hour=st.nextToken();
			String minute = st.nextToken();
			String second = st.nextToken();
			if(hour.length()==1)
				hour="0"+hour;
			if(minute.length()==1)
				minute="0"+minute;
			if(second.length()==1)
				second="0"+second;
			return (hour+":"+minute+":"+second);			
		}		
		
	// Method added by Murugesh on 04/04/2006
	public static String nextMonthFirstDay(String date)
	{
		String next_month_first_day=null;
		if(date!=null){
			StringTokenizer st=new StringTokenizer(date,"/");
			int day=Integer.parseInt(st.nextToken());
			int month=Integer.parseInt(st.nextToken());
			int year=Integer.parseInt(st.nextToken());
			String temp_month=null;
			String temp_day="01";
			if(month==12){
				temp_month="01";
				year++;
			}
			else{
				if(month<9)
					temp_month="0"+(++month);
				else
					temp_month=String.valueOf(++month);
			}
			
			next_month_first_day=temp_day+"/"+temp_month+"/"+String.valueOf(year);
		}
		return next_month_first_day;
	}
	
//	 Method added by Murugesh on 04/04/2006
	public static String previousMonthFirstDay(String date)
	{
		String previous_month_first_day=null;
		if(date!=null){
			StringTokenizer st=new StringTokenizer(date,"/");
			int day=Integer.parseInt(st.nextToken());
			int month=Integer.parseInt(st.nextToken());
			int year=Integer.parseInt(st.nextToken());
			String temp_month=null;
			String temp_day="01";
			if(month==01){
				temp_month="12";
				year--;
			}
			else{
				if(month<=10)
					temp_month="0"+(--month);
				else
					temp_month=String.valueOf(--month);
			}
			
			previous_month_first_day=temp_day+"/"+temp_month+"/"+String.valueOf(year);
		}
		return previous_month_first_day;
	}
	
	// Method added by Murugesh on 06/07/2006
	public static String lastDayOfMonth(String date)
	{
		String next_month_first_day=null;
		String last_day_of_month=null;
		try{
			if(date!=null){
				next_month_first_day = nextMonthFirstDay(date);
				last_day_of_month = addDays(next_month_first_day,-1);
			}
		}catch(Exception e){e.printStackTrace();}
		return last_day_of_month;
	}
	
	/*// Method added by sanjeet.
	public static void exitform(){
		MainScreen.jsp[0].removeAll();
		MainScreen.js.removeAll();
		MainScreen.jsp[0].repaint();
		MainScreen.js.repaint();
		//MainScreen.js.;
	}*/
	
	public static String getTenthDate(String str){
		StringTokenizer st=new StringTokenizer(str,"/");
		String dd=st.nextToken();
		String mm=st.nextToken();
		String yy=st.nextToken();
		
		return yy+"-"+mm+"-"+"10"; 
		
	}
}
