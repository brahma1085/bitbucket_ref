
package masterObject.general;

import java.io.Serializable;

public class UserVerifier implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
String vid,vdatetime,vtml_no,uid,utml_no,udatetime;

public String getUserId(){return uid;}
public String getUserTml(){return utml_no;}
public String getUserDate(){return udatetime;}

public String getVerId(){return vid;}
public String getVerDate(){return vdatetime;}
public String getVerTml(){return vtml_no;}


public void setVerId(String vid){this.vid = vid;}
public void setVerDate(String vdatetime){this.vdatetime = vdatetime;}
public void setVerTml(String vtml_no){this.vtml_no = vtml_no;}

public void setUserId(String uid){this.uid=uid;}
public void setUserDate(String udatetime){this.udatetime = udatetime;}
public void setUserTml(String utml_no){this.utml_no=utml_no;}




}
