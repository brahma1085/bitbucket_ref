package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;

import java.io.Serializable;

public class SignatureInstructionObject implements Serializable
{
    static final long serialVersionUID = 1L;//ship.....20/01/2007
    
	String ac_ty;
	int ac_no;
	String name,desg,type_op,occupation;
	byte[] photo,sign;
	int min_lmt,max_lmt,cid,category;

	public int getCid(){return cid;}
	public void setCid(int cid){this.cid=cid;}
	
	public int getCategory(){return category;}
	public void setCategory(int category){this.category=category;}
	
	public int getSAccNo(){return ac_no;}
	public void setSAccNo(int ac_no){this.ac_no=ac_no;}

	public String getSAccType(){return ac_ty;}
	public void setSAccType(String ac_ty){this.ac_ty=ac_ty;}
	
	public String getName(){return name;}
	public void setName(String name){this.name=name;}
	
	public int getMinLimit(){return min_lmt;}
	public void setMinLimit(int min_lmt){this.min_lmt=min_lmt;}	

	public int getMaxLimit(){return max_lmt;}	
	public void setMaxLimit(int max_lmt){this.max_lmt=max_lmt;}

	public String getDesignation(){return desg;}
	public void setDesignation(String desg){this.desg=desg;}
	

	public void setPhoto(byte[] photo){this.photo=photo;}
	public byte[] getPhoto(){return photo;}

	public void setSignature(byte[] sign){this.sign=sign;}
	public byte[] getSignature(){return sign;}
	
	public void setTypeOfOperation(String type_op){this.type_op=type_op;}
	public String getTypeOfOperation(){return type_op;}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	
}
