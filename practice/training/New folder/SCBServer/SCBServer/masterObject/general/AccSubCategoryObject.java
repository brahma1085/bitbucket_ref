package masterObject.general;
import java.io.Serializable;

public class AccSubCategoryObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String catdesc,age;
	int catcode;

	UserVerifier uv=new UserVerifier();

	public String getSubCategoryDesc(){ return catdesc;}
	public void setSubCategoryDesc(String modd){this.catdesc=modd;}

	public int getSubCategoryCode(){ return catcode;}
	public void setSubCategoryCode(int modc){this.catcode=modc;}

	public String getAge(){ return age;}
	public void setAge(String age){this.age=age;}

}
