package SRC.COM.SUNRISE.UTILITY.VALUEOBJECT;



import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;



public class CustomerMasterObject {

	
	private  int cid;
    private  String fname,mname,lname,others,panno,gname,occup,dob,gaddress;
    private  String sex,gsex,maritals,salute,gsalute,custtype,nameproof,addrproof,suboccup,faname,maname,husname;
    private  int op,category,subcat;
    private String introno,ppno,issuedate,expirydate,scst,sign,photo,grelation,courtdate,gtype,caste,spousename;
    private  byte bimage[],bsimage[];
    
    
    private String  address;
    private long mobile,phone,pin;
    int add_type;
    
    private  String vid,vdatetime,vtml_no,uid,utml_no,udatetime;
    
    
    private String nationality;
    private String religion,br_code;
    
    public void setCategory(int s){category=s;}
    public int getCategory(){return category;}
    
   
    
    
    public void setBinaryImage(byte str[]){bimage=str;}
    public byte[] getBinaryImage(){return bimage;}
    
    public void setBinarySignImage(byte str[]){bsimage=str;}
    public byte[] getBinarySignImage(){return bsimage;}
    
    public int getSubCategory(){return subcat;}
    public void setSubCategory(int a){subcat=a;}
    
    public void setScSt(String str){scst=str;}
    public String getScSt(){return scst;}
    
    public void setCaste(String str){caste=str;}
    public String getCaste(){return caste;}
    
    
    public void setPassPortNumber(String str){ppno=str;}
    public String getPassPortNumber(){return ppno;}
    
    public void setFatherName(String fanme){this.faname=fanme;}
    public String getFatherName(){return faname;}
    
    public void setMotherName(String fanme){this.maname=fanme;}
    public String getMotherName(){return maname;}
    
    
    public void setPPIssueDate(String str){issuedate=str;}
    public String getPPIssueDate(){return issuedate;}
    
    public void setPPExpiryDate(String str){expirydate=str;}
    public String getPPExpiryDate(){return expirydate;}
    
    
    public void setOperation(int op){this.op=op;}
    public int getOperation(){return op;}
    
    
    public String getAddressProof(){return addrproof;}
    public void  setAddressProof(String type){addrproof=type;}
    
    public String getNameProof(){return nameproof;}
    public void  setNameProof(String type){nameproof=type;}
    
    public String getSubOccupation(){return suboccup;}
    public void  setSubOccupation(String type){suboccup=type;}
    
    public String getIntroducerId(){return introno;}
    public void  setIntroducerId(String type){introno=type;}
    
    
    public int getCustomerID(){return cid;}
    public void setCustomerID(int cid){this.cid=cid;}
    
    public String getFirstName(){return fname;}
    public void setFirstName(String fname){this.fname=fname;}
    
    public String getMiddleName(){return mname;}
    public void setMiddleName(String mname){this.mname=mname;}
    
    public String getLastName(){return lname;}
    public void setLastName(String lname){this.lname=lname;}
    
    public String getName(){return (fname+" "+mname+" "+lname);}
    
    public String getSalute(){return salute;}
    public void setSalute(String salute){this.salute=salute;}
    
    public String getDOB(){return dob;}
    public void setDOB(String dob){this.dob=dob;}
    
    public String getSex(){return sex;}
    public void setSex(String sex){this.sex=sex;}
    
    public String getMaritalStatus(){return maritals;}
    public void setMaritalStatus(String maritals){this.maritals=maritals;}
    
    
    public String getPanno(){return panno;}
    public void setPanno(String panno){this.panno=panno;}
    
    public String getOccupation(){return occup;}
    public void setOccupation(String occup){this.occup=occup;}
    
    public String getGuardName(){return gname;}
    public void setGuardName(String gname){this.gname=gname;}
    
    public String getGuardSalute(){return gsalute;}
    public void setGuardSalute(String gsalute){this.gsalute=gsalute;}
    
    public String getGuardAddress(){return gaddress;}
    public void setGuardAddress(String gaddress){this.gaddress=gaddress;}
    
    public String getGuardSex(){return gsex;}
    public void setGuardSex(String gsex){this.gsex=gsex;}
    
    public String getGuardRelationship(){return grelation;}
    public void setGuardRelationship(String rel){grelation=rel;}
    
    public String getGuardType(){return gtype;}
    public void setGuardType(String gaddress){this.gtype=gaddress;}
    
    public String getCourtDate(){return courtdate;}
    public void setCourtDate(String rel){courtdate=rel;}
    
    public String getPhoto(){ return photo;}
    public void setPhoto(String photo){this.photo=photo;}
    
    public String getSign(){ return sign;}
    public void setSign(String sign){this.sign=sign;}
    
    
    
    public String getNationality() {
        return nationality;
    }
    
    public void setNationality(String na) {
        nationality=na;
    }
    
    public String getReligion() {
        return religion;
    }
    public void setReligion(String str)
    {
        religion=str;
    }
    public void setBranchCode(String br_code){this.br_code=br_code;}
    public String getBranchCode(){return br_code;}
	public String getUdatetime() {
		return udatetime;
	}
	public void setUdatetime(String udatetime) {
		this.udatetime = udatetime;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUtml_no() {
		return utml_no;
	}
	public void setUtml_no(String utml_no) {
		this.utml_no = utml_no;
	}
	public String getVdatetime() {
		return vdatetime;
	}
	public void setVdatetime(String vdatetime) {
		this.vdatetime = vdatetime;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getVtml_no() {
		return vtml_no;
	}
	public void setVtml_no(String vtml_no) {
		this.vtml_no = vtml_no;
	}
	public int getAdd_type() {
		return add_type;
	}
	public void setAdd_type(int add_type) {
		this.add_type = add_type;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public long getPin() {
		return pin;
	}
	public void setPin(long pin) {
		this.pin = pin;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSpousename() {
		return spousename;
	}
	public void setSpousename(String spousename) {
		this.spousename = spousename;
	}
	
	
	
}
