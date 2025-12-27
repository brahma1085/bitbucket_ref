/*** vasu ***/
package masterObject.clearing;

public class ChequeAckObject implements java.io.Serializable
{
	String string_trn_type,string_clg_type,string_doc_bs,string_de_user,string_source_name,string_bank_name,string_de_time,string_dest_name,string_ve_user,string_ve_tml,string_de_tml,string_cr_ac_type,string_bank_code;
	int int_ctrl_no,controlNum,int_ack_no,int_doc_source,int_doc_destin,int_no_of_docs,int_cr_ac_no;
	double double_doc_total,docTotall;
	String tranTyp,docBss,srcName,chequeNo,destinName,creditAcNum,bnkName,ackNum;
	
	
	
	
	
	public int getControlNo(){return int_ctrl_no;}
	public void setControlNo(int int_ctrl_no){this.int_ctrl_no=int_ctrl_no;}

	public String getTranType(){return string_trn_type;}
	public void setTranType(String string_trn_type){this.string_trn_type=string_trn_type;}

	public String getClgType(){return string_clg_type;}
	public void setClgType(String string_clg_type){this.string_clg_type=string_clg_type;}

	public int getAckNo(){return int_ack_no;}
	public void setAckNo(int int_ack_no){this.int_ack_no=int_ack_no;}

	public int getDocSource(){return int_doc_source;}
	public void setDocSource(int int_doc_source){this.int_doc_source=int_doc_source;}

	public int getDocDestination(){return int_doc_destin;}
	public void setDocDestination(int int_doc_destin){this.int_doc_destin=int_doc_destin;}

	public String getDocBs(){return string_doc_bs;}
	public void setDocBs(String string_doc_bs){this.string_doc_bs=string_doc_bs;}

	public int getNoOfDocs(){return int_no_of_docs;}
	public void setNoOfDocs(int int_no_of_docs){this.int_no_of_docs=int_no_of_docs;}

	public double getDocTotal(){return double_doc_total;}
	public void setDocTotal(double double_doc_total){this.double_doc_total=double_doc_total;}

	public String getCreditACType(){return string_cr_ac_type;}
	public void setCreditACType(String string_cr_ac_type){this.string_cr_ac_type=string_cr_ac_type;}

	public int getCreditACNo(){return int_cr_ac_no;}
	public void setCreditACNo(int int_cr_ac_no){this.int_cr_ac_no=int_cr_ac_no;}

	public String getBankCode(){return string_bank_code;}
	public void setBankCode(String string_bank_code){this.string_bank_code = string_bank_code;}

	public String getDeTml(){return string_de_tml;}
	public void setDeTml(String string_de_tml){this.string_de_tml=string_de_tml;}

	public String getDeUser(){return string_de_user;}
	public void setDeUser(String string_de_user){this.string_de_user=string_de_user;}
	
	public String getVeTml(){return string_ve_tml;}
	public void setVeTml(String string_ve_tml){this.string_ve_tml=string_ve_tml;}

	public String getVeUser(){return string_ve_user;}
	public void setVeUser(String string_ve_user){this.string_ve_user=string_ve_user;}

	public String getSourceName(){return string_source_name;}
	public void setSourceName(String string_source_name){this.string_source_name=string_source_name;}

	public String getDestName(){return string_dest_name;}
	public void setDestName(String dname){this.string_dest_name=dname;}
	
	public String getDeTime(){return string_de_time;}
	public void setDeTime(String string_de_time){this.string_de_time=string_de_time;}

	public String getBankName(){return string_bank_name;}
	public void setBankName(String string_bank_name){this.string_bank_name=string_bank_name;}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	
	public int getControlNum() {
		return controlNum;
	}
	public void setControlNum(int controlNum) {
		this.controlNum = controlNum;
	}
	public String getTranTyp() {
		return tranTyp;
	}
	public void setTranTyp(String tranTyp) {
		this.tranTyp = tranTyp;
	}
	
	public double getDocTotall() {
		return docTotall;
	}
	public void setDocTotall(double docTotall) {
		this.docTotall = docTotall;
	}
	public String getDestinName() {
		return destinName;
	}
	public void setDestinName(String destinName) {
		this.destinName = destinName;
	}
	public String getCreditAcNum() {
		return creditAcNum;
	}
	public void setCreditAcNum(String creditAcNum) {
		this.creditAcNum = creditAcNum;
	}
	public String getBnkName() {
		return bnkName;
	}
	public void setBnkName(String bnkName) {
		this.bnkName = bnkName;
	}
	public String getAckNum() {
		return ackNum;
	}
	public void setAckNum(String ackNum) {
		this.ackNum = ackNum;
	}
	public String getDocBss() {
		return docBss;
	}
	public void setDocBss(String docBss) {
		this.docBss = docBss;
	}
	public String getSrcName() {
		return srcName;
	}
	public void setSrcName(String srcName) {
		this.srcName = srcName;
	}
	

}