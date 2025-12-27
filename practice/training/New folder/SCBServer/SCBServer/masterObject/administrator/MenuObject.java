package masterObject.administrator;
import java.io.*;

public class MenuObject implements Serializable
{
	String name,code;
	
	public void setMenuName(String name)	
	{
		this.name=name;
	}
	public String getMenuName()	
	{
		return name;
	}
	
	
	public void setMenuCode(String code)	
	{
		this.code=code;
	}
	public String getMenuCode()	
	{
		return code;
	}
}