
package masterObject.administrator;

import java.io.Serializable;

public class TreeObject implements Serializable {
 String menu_name,menu_code,tml_no;
  
 	public String getMenuName(){ return menu_name;}
	public void setMenuName(String menu_name){this.menu_name=menu_name;}
	
	public String getMenuCode(){ return menu_code;}
	public void setMenuCode(String menu_code){this.menu_code=menu_code;}
	
	public String getTmlNo() { return tml_no;}
	public void setTmlNo(String tml_no){this.tml_no=tml_no;}
	
}
