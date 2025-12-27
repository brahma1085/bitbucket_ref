package SRC.COM.SUNRISE.UTILITY;



//import exceptions.DateFormatException;

//import general.Validations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import SRC.COM.SUNRISE.SERVER.GetDBConnection;
import SRC.COM.SUNRISE.UTILITY.EXCEPTION.DateFormateException;

;





public class Validation {

	
	public static String getSysDate() 
    {  
		Calendar c = Calendar.getInstance();

		try 
        {
			return (Validation.checkDate(c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR)));
		} 
        catch(DateFormateException e) 
        {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String checkDate(String date) throws DateFormateException
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
				throw new DateFormateException("Invalid Date Format");
			}
			else if(mm.length()>2 || Integer.parseInt(mm)>12 || Integer.parseInt(mm)==0)
			{
				throw new DateFormateException("Invalid Month Format");
			}
			else if((yyyy.length()==1 || yyyy.length()==3 ||yyyy.length()>=5 || Integer.parseInt(yyyy)==0) && !(yyyy.equalsIgnoreCase("10000")))
			{
				throw new DateFormateException("Invalid Year Format");
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
	        throw new DateFormateException("Date Problem"); 
	    }catch(NumberFormatException nf ) {
	    	throw new DateFormateException("Invalid Date Format"); 
	    }
	}
	
	
	
	public static String addDays(String date,int interval) throws DateFormateException
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
			gbc.add(Calendar.DATE,interval);

		}catch(Exception exe){exe.printStackTrace();}
		
		return (Validation.checkDate(gbc.get(Calendar.DATE)+"/"+(gbc.get(Calendar.MONTH)+1)+"/"+gbc.get(Calendar.YEAR)));
	}
	
	public static String addNoOfMonths(String date,int no_of_months) throws DateFormateException
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
		
		return (Validation.checkDate(gbc.get(Calendar.DATE)+"/"+(gbc.get(Calendar.MONTH)+1)+"/"+gbc.get(Calendar.YEAR)));
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
	}catch(Exception ex){
		ex.printStackTrace();
	}


	return days;
	}
	
	
		
	public static String subyear(String date ,int no_of_year) {
		
		String dat = null;
		StringTokenizer token = new StringTokenizer(date,"/");
		
		dat = token.nextToken()+"/"+token.nextToken()+"/"+(new Integer((String)token.nextElement()).intValue() - no_of_year);
		
		System.out.println(dat);
		
		return dat;
	}
	public static String getFutureMonthDate(String cur_date,int month) 
	{ 
		Connection conn = null;
		Statement stmt = null;
		String string_date=cur_date;
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select DATE_FORMAT(DATE_ADD('"+Validation.convertYMD(cur_date)+"', INTERVAL "+month+" MONTH),'%d/%m/%Y')");
			rs.next();
			string_date = rs.getString(1);
		}catch (SQLException exception) {
			exception.printStackTrace();
			string_date = cur_date;
		}
		return string_date;
	}
	
	
	public static String getFutureDayDate(String cur_date,int days) 
	{  
		Connection conn = null;
		Statement stmt = null;
		String string_date=cur_date;
		try
		{
			conn = GetDBConnection.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select DATE_FORMAT(DATE_ADD('"+convertYMD(cur_date)+"', INTERVAL "+days+" DAY),'%d/%m/%Y')");
			rs.next();
			string_date = rs.getString(1);
		}catch (SQLException exception) {
			exception.printStackTrace();
			string_date = cur_date;
		}
		/*finally{
			try {
				//conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
		return string_date;
	}
	
	public static String convertYMD(String str)
	{
		StringTokenizer st=new StringTokenizer(str,"/");
		String dd=st.nextToken();
		String mm=st.nextToken();
		String yy=st.nextToken();
		return (yy+"-"+mm+"-"+dd);
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
	
}
