package masterObject.backOffice;

import java.io.Serializable;

public class UserObject implements Serializable
{
	private  String uid,pwd,name,sname,tml;
	
	public String getUid(){return uid;}
	public void setUid(String uid){this.uid=uid;}

	public String getPwd(){return pwd;}
	public void setPwd(String pwd){this.pwd=pwd;}

	public String getName(){return name;}
	public void setName(String name){this.name=name;}

	public String getSName(){return sname;}
	public void setSName(String sname){this.sname=sname;}

	public String getTml(){return tml;}
	public void setTml(String tml){this.tml=tml;}

}