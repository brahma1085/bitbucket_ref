/*
 * Created on May 25, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package masterObject.generalLedger;

import java.io.Serializable;

/**
 * @author Swapna.B
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Form1Object implements Serializable {

    String name,fromdate,todate,de_user,de_tml,de_dt_time,gl_type,gl_abbr,gl_name,cd_ind,trn_src,time,date,dayofweek;
    int code,gl_code,mul_by,recurring_days,sequence;
    double percent,ndtlamount;
    
    public void setCode(int code){this.code=code;}
    public int getCode(){return this.code;}
    
    public void setName(String name){this.name=name;}
    public String getName(){return this.name;}
    
    public void setFromDate(String fromdate){this.fromdate=fromdate;}
    public String getFromDate(){return this.fromdate;}
    
    public void setToDate(String todate){this.todate=todate;}
    public String getToDate(){return this.todate;}
    
    public void setDeUser(String de_user){this.de_user=de_user;}
    public String getDeUser(){return this.de_user;}
    
    public void setDeTml(String de_tml){this.de_tml=de_tml;}
	public String getDeTml(){return this.de_tml;}
	
	public void setDeDate(String de_dt_time){this.de_dt_time=de_dt_time;}
	public String getDeDate(){return this.de_dt_time;}
	
	public void setGLName(String gl_name){this.gl_name=gl_name;}
	public String getGLName(){return this.gl_name;}
	
	public void setGLCode(int gl_code){this.gl_code=gl_code;}
	public int getGLCode(){return this.gl_code;}
	
	public void setPercent(double percent){this.percent=percent;}
	public double getPercent(){ return this.percent;}
	
	public void setGLAbbr(String gl_abbr){this.gl_abbr=gl_abbr;}
	public String getGLAbbr(){return this.gl_abbr;}
	
	public void setTrnSrc(String trn_src){this.trn_src=trn_src;}
	public String getTrnSrc(){return this.trn_src;}
	
	public void setCdInd(String cd_ind){this.cd_ind=cd_ind;}
	public String getCdInd(){return this.cd_ind;}
	
	public void setDate(String date){this.date=date;}
	public String getDate(){return this.date;}
	
	public void setTime(String time){this.time=time;}
	public String getTime(){return this.time;}
	
	public void setGLType(String gl_type){this.gl_type =gl_type;}
	public String getGLType(){return this.gl_type ;}

	public void setMulBy(int mul_by){this.mul_by =mul_by;}
	public int getMulBy(){return this.mul_by ;}
	
	public void setNDTLAmount(double ndtlamount){this.ndtlamount=ndtlamount;}
	public double getNDTLAmount(){return this.ndtlamount;}
	
	public void setDayOfWeek(String dayofweek){this.dayofweek=dayofweek;}
	public String getDayOfWeek(){return this.dayofweek;}
	
	public void setRecurringDays(int recurring_days){this.recurring_days =recurring_days;}
	public int getRecurringDays(){return this.recurring_days;}
	
	public void setSequence(int sequence){this.sequence=sequence;}
    public int getSequence(){return this.sequence;}
    
	
}
