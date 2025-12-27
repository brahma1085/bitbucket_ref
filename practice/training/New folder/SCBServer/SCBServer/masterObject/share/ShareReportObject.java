package masterObject.share;
import java.io.Serializable;

import javax.swing.JCheckBox;

public class ShareReportObject implements Serializable
{

int shareNumber,customerId,branchCode,share_cert_number,numberofShares,pin,acc_no,nomineeRegNo,cert_no_f,cert_no_t,intrAccno,share_bal,payaccno;
String transDate,transType,shareStatus,shacctype,name,issueDate,cert_date,branchName,sex,address,city,shareType,acc_ty,intrname,nomname,nomrel,divUptoDate,intr_acctype,transNarr,cd_ind,paymode,recmode,scst,payacctype,loanAvailed,acc_status,mem_cat;
double shareVal,debitAmount,creditAmount,per_shrvalue;
String deUserId,veUserId;
//JCheckBox check_box=new JCheckBox();
String check_box="<html><input type='checkbox' ></html>";


public String getCheck_box() {
	return check_box;
}
public void setCheck_box(String check_box) {
	this.check_box = check_box;
}
public String getShareAccType(){return shacctype;}
public void setShareAccType(String  str){this.shacctype=str;}

public String getReceivedMode(){return recmode;}
public void setReceivedMode(String pay_mode){this.recmode=pay_mode;}

public String getScSt(){return scst;}
public void setScSt(String scst){this.scst=scst;}

public String getLoanAvailed(){return loanAvailed;}
public void setLoanAvailed(String loan_availed){this.loanAvailed = loan_availed;}

public String getPayMode(){return paymode;}
public void setPayMode(String paymode){this.paymode=paymode;}

public void setTransNarr(String trn_narr){this.transNarr=trn_narr;}
public String getTransNarr(){return transNarr;}

public void setcd_ind(String cd_ind){this.cd_ind=cd_ind;}
public String getcd_ind(){return cd_ind;}

public void setDebitAmount(double dbamt){this.debitAmount=dbamt;}
public double getDebitAmount(){return debitAmount;}

public void setCreditAmount(double camt){this.creditAmount=camt;}
public double getCreditAmount(){return creditAmount;}

public int getshare_bal(){return share_bal;}
public void setshare_bal(int share_bal){this.share_bal=share_bal;}

public void setShareNumber(int share_number){this.shareNumber = share_number;}
public int getShareNumber(){return shareNumber;}

public void setAccno(int acc_no){this.acc_no = acc_no;}
public int getAccno(){return acc_no;}

public void setAcctype(String acc_ty){this.acc_ty = acc_ty;}
public String getAcctype(){return acc_ty;}

public void setTransDate(String trn_date){this.transDate = trn_date;}
public String getTransDate(){return transDate;}

public void setTransType(String trn_type){this.transType = trn_type;}
public String getTransType(){return transType;}


public void setDivUptoDate(String div_uptodate){this.divUptoDate=div_uptodate;}
public String getDivUptoDate(){return divUptoDate;}

public void setNomineeName(String nomname){this.nomname = nomname;}
public String getNomineeName(){return nomname;}

public void setNomineeRelation(String nomrel){this.nomrel = nomrel;}
public String getNomineeRelation(){return nomrel;}

public void setIntrName(String intrname){this.intrname = intrname;}
public String getIntrName(){return intrname;}

public void setIntrAccno(int intr_accno){this.intrAccno = intr_accno;}
public int getIntrAccno(){return intrAccno;}

public void setNomineeRegNo(int no){this.nomineeRegNo=no;}
public int getNomineeRegNo(){return nomineeRegNo;}

public void setIntrAcctype(String intr_acctype){this.intr_acctype = intr_acctype;}
public String getIntrAcctype(){return intr_acctype;}

public void setShareStatus(String shr_status){this.shareStatus = shr_status;}
public String getShareStatus(){return shareStatus;}

public void setCustomerId(int id){customerId=id;}
public int  getCustomerId(){return customerId;}

public void setName(String na){name=na;}
public String getName(){return name;}

public void setSex(String sex){this.sex=sex;}
public String getSex(){return sex;}

public void setBranchCode(int bcode){this.branchCode=bcode;}
public int getBranchCode(){return branchCode;}

public void setShareCertNumber(int share_cert_number){this.share_cert_number = share_cert_number;}
public int getShareCertNumber(){return share_cert_number;}

public void setCertNumberFrom(int cert_no_f){this.cert_no_f=cert_no_f;}
public int getCertNumberFrom(){return cert_no_f;}

public void setCertNumberTo(int cert_no_t){this.cert_no_t=cert_no_t;}
public int getCertNumberTo(){return cert_no_t;}

public void setShareCertDate(String cert_date){this.cert_date = cert_date;}
public String getShareCertDate(){return cert_date;}

public void setNumberofShares(int num_shares){this.numberofShares=num_shares;}
public int getNumberofShares(){	return numberofShares;}

public double getShareVal(){return shareVal;}
public void setShareVal(double share_val){this.shareVal=share_val;}

public String getIssueDate(){return issueDate;}
public void setIssueDate(String st){this.issueDate=st;}

public String getShareType(){return shareType;}
public void setShareType(String sh_ty){this.shareType=sh_ty;}

public String getBranchName(){return branchName;}
public void setBranchName(String bname){this.branchName=bname;}

public void setAddress(String add){address=add;}
public String getAddress(){return address;}


public void setCity(String city){this.city=city;}
public String getCity(){return city;}

public void setPin(int pin){this.pin=pin;}
public int getPin(){return pin;}



public int getPaymentAccno(){return payaccno;}
public void setPaymentAccno(int accno){this.payaccno=accno;}


public String getPaymentAcctype(){return payacctype;}
public void setPaymentAcctype(String acctype){this.payacctype=acctype;}

public double getPerShrValue(){return per_shrvalue;}//Karthi
public void setPerShrValue(double per_val){this.per_shrvalue=per_val;}

public void setAccStatus(String status){this.acc_status=status;}//Karthi
public String getAccStatus(){return acc_status;}

public String getdeUserId(){return deUserId;}//Karthi
public void setdeUserId(String user_id){this.deUserId=user_id;}

public String getVeUserId(){return veUserId;}//Karthi
public void setVeUserId(String veuser_id){this.veUserId=veuser_id;}
public String getMem_cat() {
	return mem_cat;
}
public void setMem_cat(String mem_cat) {
	this.mem_cat = mem_cat;
}


}
