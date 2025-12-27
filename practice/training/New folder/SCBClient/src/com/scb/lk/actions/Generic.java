package com.scb.lk.actions;

import java.util.StringTokenizer;

public class Generic {
	
	
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

}
