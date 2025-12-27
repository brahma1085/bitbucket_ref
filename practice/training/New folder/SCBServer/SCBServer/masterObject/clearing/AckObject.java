/*** vasu ***/
package masterObject.clearing;

import masterObject.general.BranchObject;

public class AckObject implements java.io.Serializable
{
	int int_ackno,int_doc_source,int_no_of_docs;
	String string_ackdate,string_reconciled,string_branchname,string_bankcode,string_bankname,string_clg_type,string_vetml;
	double double_totalamount,double_doctotal,double_ack_remainingamount;
	String clg_type;
	public BranchObject branchobject=new BranchObject();

	private String clgType;

	
	public int getAckNo(){return int_ackno;}
	public void setAckNo(int int_ackno){this.int_ackno=int_ackno;}

	public String getAckDate(){return string_ackdate;}
	public void setAckDate(String string_ackdate){this.string_ackdate=string_ackdate;}

	public int getDocSource(){return int_doc_source;}
	public void setDocSource(int int_doc_source){this.int_doc_source=int_doc_source;}

	public String getBranch(){return string_branchname;}
	public void setBranch(String string_branchname){this.string_branchname=string_branchname;}

	public double getTotal(){return double_totalamount;}
	public void setTotal(double double_totalamount){this.double_totalamount=double_totalamount;}

	public String getReconciled(){return string_reconciled;}
	public void setReconciled(String string_reconciled){this.string_reconciled=string_reconciled;}

	public double getAckEntered(){return double_ack_remainingamount;}
	public void setAckEntered(double double_ack_remainingamount){this.double_ack_remainingamount=double_ack_remainingamount;}

//===========Details from Cheque(control No)-----------
	public int getNoDocs(){return int_no_of_docs;}
	public void setNoDocs(int int_no_of_docs){this.int_no_of_docs=int_no_of_docs;}
	
	public String getClgType(){return clgType;}
	public void setClgType(String string_clg_type){this.clgType=string_clg_type;}


	public double getDocTotal(){return double_doctotal;}
	public void setDocTotal(double double_doctotal){this.double_doctotal=double_doctotal;}

	public String getBankCode(){return string_bankcode;}
	public void setBankCode(String string_bankcode){this.string_bankcode=string_bankcode;}

	public String getBankName(){return string_bankname;}
	public void setBankName(String string_bankname){this.string_bankname=string_bankname;}

	public String getVeTml(){return string_vetml;}
	public void setVeTml(String string_vetml){this.string_vetml=string_vetml;}
	
	public String getClg_type() { return clg_type; }
	
	public void setClg_type(String clg_type) { this.clg_type = clg_type; }


}