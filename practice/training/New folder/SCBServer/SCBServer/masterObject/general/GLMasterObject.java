package masterObject.general;
import java.io.Serializable;

public class GLMasterObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String gl_type, gl_code, gl_name, sch_type, gl_status, normal_cd;
	private String gl_abbrv;

	public String getGLType(){ return gl_type;}
	public void setGLType(String gl_type){this.gl_type=gl_type;}

	public String getGLCode(){ return gl_code;}
	public void setGLCode(String gl_code){this.gl_code=gl_code;}

	public String getGLName(){ return gl_name;}
	public void setGLName(String gl_name){this.gl_name=gl_name;}

	public String getSCHType(){ return sch_type;}
	public void setSCHType(String sch_type){this.sch_type=sch_type;}

	public String getGLStatus(){ return gl_status;}
	public void setGLStatus(String gl_status){this.gl_status=gl_status;}

	public String getNormalCd(){return normal_cd;}
	public void setNormalCd(String normal_cd){this.normal_cd=normal_cd;}
	public void setGLAbbreviation(String string) {
		this.gl_abbrv=string;
		
	}
	public String getGLAbbreviation()
	{
		return gl_abbrv;
	}

}
