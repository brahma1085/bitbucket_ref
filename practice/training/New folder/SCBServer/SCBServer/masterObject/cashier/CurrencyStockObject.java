package masterObject.cashier;

import java.io.Serializable;
   
public class CurrencyStockObject implements Serializable 
{
    static final long serialVersionUID = 1L;//ship.....02/12/2006
    
	private String string_tml_no, string_rec_type,string_netamt,string_tml_id;
	private int int_s1000,int_s500,int_s100,int_s50,int_s20,int_s10,int_s5,int_s2,int_s1;
	private double double_scoins;
/*	public UserVerifier uv;
	
	public CurrencyStockObject()
	{
	uv=new UserVerifier();
	}*/
	public String getTmlNo(){return string_tml_no;}
	public void setTmlNo(String string_tml_no){this.string_tml_no=string_tml_no;}
	
	public String getRecType(){return string_rec_type;}
	public void setRecType(String string_rec_type){this.string_rec_type=string_rec_type;}
	
	public String getNetamt(){return string_netamt;}
	public void setNetamt(String string_netamt){this.string_netamt=string_netamt;}
	
	public int gets1000(){return int_s1000;}
	public void sets1000(int int_s1000){this.int_s1000=int_s1000;}
	
	public int gets500(){return int_s500;}
	public void sets500(int int_s500){this.int_s500=int_s500;}
	
	public int gets100(){return int_s100;}
	public void sets100(int int_s100){this.int_s100=int_s100;}
	
	public int gets50(){return int_s50;}
	public void sets50(int int_s50){this.int_s50=int_s50;}
	
	public int gets20(){return int_s20;}
	public void sets20(int int_s20){this.int_s20=int_s20;}
	
	public int gets10(){return int_s10;}
	public void sets10(int int_s10){this.int_s10=int_s10;}
	
	public int gets5(){return int_s5;}
	public void sets5(int int_s5){this.int_s5=int_s5;}
	
	public int gets2(){return int_s2;}
	public void sets2(int int_s2){this.int_s2=int_s2;}
	
	public int gets1(){return int_s1;}
	public void sets1(int int_s1){this.int_s1=int_s1;}
	
	public double getscoins(){return double_scoins;}
	public void setscoins(double double_scoins){this.double_scoins=double_scoins;}	
	
	//ship.....16/01/2006
	public String getTmlId(){return string_tml_id;}
	public void setTmlId(String string_tml_id){this.string_tml_id=string_tml_id;}
	//
}