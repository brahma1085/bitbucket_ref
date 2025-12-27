package masterObject.general;
import java.io.Serializable;
import masterObject.general.UserVerifier;
public class AccCategoryObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String catdesc;
	int catcode;

	UserVerifier uv=new UserVerifier();

	public String getCategoryDesc(){ return catdesc;}
	public void setCategoryDesc(String modd){this.catdesc=modd;}

	public int getCategoryCode(){ return catcode;}
	public void setCategoryCode(int modc){this.catcode=modc;}

}
