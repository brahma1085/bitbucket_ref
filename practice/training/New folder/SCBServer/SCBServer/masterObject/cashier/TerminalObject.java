package masterObject.cashier;
import java.io.Serializable;
   
public class TerminalObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....02/12/2006
    
    private String string_tml_code,string_tml_desc,string_data_entry_usr,string_tml_type,string_data_date;
	
	public String getTerminals(){return string_tml_code;}
	public void setTerminals(String string_tml_code){this.string_tml_code=string_tml_code;}
	
	public String getTerminalDesc(){return string_tml_desc;}
	public void setTerminalDesc(String string_tml_desc){this.string_tml_desc=string_tml_desc;}
	
	public String getDataEntryUser(){return string_data_entry_usr;}
	public void setDataEntryUser(String string_data_entry_usr){this.string_data_entry_usr=string_data_entry_usr;}
	
	public String getTerminalType(){return string_tml_type;}
	public void setTerminalType(String string_tml_type){this.string_tml_type=string_tml_type;}
	
	public String getDataDate(){return string_data_date;}
	public void setDataDate(String string_data_date){this.string_data_date=string_data_date;}
}