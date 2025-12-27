package com.banking.data.pojo;
// default package



/**
 * CustomerMasterLogId entity. @author MyEclipse Persistence Tools
 */

public class CustomerMasterLogId  implements java.io.Serializable {


    // Fields    

     private Integer brCode;
     private Integer cid;
     private Integer custtype;
     private Integer introid;
     private String fname;
     private String mname;
     private String lname;
     private String salute;
     private String mothername;
     private String fathername;
     private String husbandname;
     private String dob;
     private String sex;
     private String marital;
     private Integer subCategory;
     private String photo;
     private String sign;
     private String nameproof;
     private String addrproof;
     private String panno;
     private String occupation;
     private String suboccupation;
     private String scst;
     private String caste;
     private String nationality;
     private String religion;
     private String passportno;
     private String dateofissue;
     private String dateofexpiry;
     private String guardiantype;
     private String guardianname;
     private Integer guardiansalute;
     private String guardiansex;
     private String guardianrelation;
     private String guardianaddress;
     private String courtdate;
     private String deUser;
     private String deTml;
     private String deDate;
     private String veUser;
     private String veTml;
     private String veDate;


    // Constructors

    /** default constructor */
    public CustomerMasterLogId() {
    }

	/** minimal constructor */
    public CustomerMasterLogId(Integer cid, String fname, String dob) {
        this.cid = cid;
        this.fname = fname;
        this.dob = dob;
    }
    
    /** full constructor */
    public CustomerMasterLogId(Integer brCode, Integer cid, Integer custtype, Integer introid, String fname, String mname, String lname, String salute, String mothername, String fathername, String husbandname, String dob, String sex, String marital, Integer subCategory, String photo, String sign, String nameproof, String addrproof, String panno, String occupation, String suboccupation, String scst, String caste, String nationality, String religion, String passportno, String dateofissue, String dateofexpiry, String guardiantype, String guardianname, Integer guardiansalute, String guardiansex, String guardianrelation, String guardianaddress, String courtdate, String deUser, String deTml, String deDate, String veUser, String veTml, String veDate) {
        this.brCode = brCode;
        this.cid = cid;
        this.custtype = custtype;
        this.introid = introid;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.salute = salute;
        this.mothername = mothername;
        this.fathername = fathername;
        this.husbandname = husbandname;
        this.dob = dob;
        this.sex = sex;
        this.marital = marital;
        this.subCategory = subCategory;
        this.photo = photo;
        this.sign = sign;
        this.nameproof = nameproof;
        this.addrproof = addrproof;
        this.panno = panno;
        this.occupation = occupation;
        this.suboccupation = suboccupation;
        this.scst = scst;
        this.caste = caste;
        this.nationality = nationality;
        this.religion = religion;
        this.passportno = passportno;
        this.dateofissue = dateofissue;
        this.dateofexpiry = dateofexpiry;
        this.guardiantype = guardiantype;
        this.guardianname = guardianname;
        this.guardiansalute = guardiansalute;
        this.guardiansex = guardiansex;
        this.guardianrelation = guardianrelation;
        this.guardianaddress = guardianaddress;
        this.courtdate = courtdate;
        this.deUser = deUser;
        this.deTml = deTml;
        this.deDate = deDate;
        this.veUser = veUser;
        this.veTml = veTml;
        this.veDate = veDate;
    }

   
    // Property accessors

    public Integer getBrCode() {
        return this.brCode;
    }
    
    public void setBrCode(Integer brCode) {
        this.brCode = brCode;
    }

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getCusttype() {
        return this.custtype;
    }
    
    public void setCusttype(Integer custtype) {
        this.custtype = custtype;
    }

    public Integer getIntroid() {
        return this.introid;
    }
    
    public void setIntroid(Integer introid) {
        this.introid = introid;
    }

    public String getFname() {
        return this.fname;
    }
    
    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return this.mname;
    }
    
    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return this.lname;
    }
    
    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSalute() {
        return this.salute;
    }
    
    public void setSalute(String salute) {
        this.salute = salute;
    }

    public String getMothername() {
        return this.mothername;
    }
    
    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getFathername() {
        return this.fathername;
    }
    
    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getHusbandname() {
        return this.husbandname;
    }
    
    public void setHusbandname(String husbandname) {
        this.husbandname = husbandname;
    }

    public String getDob() {
        return this.dob;
    }
    
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMarital() {
        return this.marital;
    }
    
    public void setMarital(String marital) {
        this.marital = marital;
    }

    public Integer getSubCategory() {
        return this.subCategory;
    }
    
    public void setSubCategory(Integer subCategory) {
        this.subCategory = subCategory;
    }

    public String getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSign() {
        return this.sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNameproof() {
        return this.nameproof;
    }
    
    public void setNameproof(String nameproof) {
        this.nameproof = nameproof;
    }

    public String getAddrproof() {
        return this.addrproof;
    }
    
    public void setAddrproof(String addrproof) {
        this.addrproof = addrproof;
    }

    public String getPanno() {
        return this.panno;
    }
    
    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getOccupation() {
        return this.occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSuboccupation() {
        return this.suboccupation;
    }
    
    public void setSuboccupation(String suboccupation) {
        this.suboccupation = suboccupation;
    }

    public String getScst() {
        return this.scst;
    }
    
    public void setScst(String scst) {
        this.scst = scst;
    }

    public String getCaste() {
        return this.caste;
    }
    
    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getNationality() {
        return this.nationality;
    }
    
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getReligion() {
        return this.religion;
    }
    
    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getPassportno() {
        return this.passportno;
    }
    
    public void setPassportno(String passportno) {
        this.passportno = passportno;
    }

    public String getDateofissue() {
        return this.dateofissue;
    }
    
    public void setDateofissue(String dateofissue) {
        this.dateofissue = dateofissue;
    }

    public String getDateofexpiry() {
        return this.dateofexpiry;
    }
    
    public void setDateofexpiry(String dateofexpiry) {
        this.dateofexpiry = dateofexpiry;
    }

    public String getGuardiantype() {
        return this.guardiantype;
    }
    
    public void setGuardiantype(String guardiantype) {
        this.guardiantype = guardiantype;
    }

    public String getGuardianname() {
        return this.guardianname;
    }
    
    public void setGuardianname(String guardianname) {
        this.guardianname = guardianname;
    }

    public Integer getGuardiansalute() {
        return this.guardiansalute;
    }
    
    public void setGuardiansalute(Integer guardiansalute) {
        this.guardiansalute = guardiansalute;
    }

    public String getGuardiansex() {
        return this.guardiansex;
    }
    
    public void setGuardiansex(String guardiansex) {
        this.guardiansex = guardiansex;
    }

    public String getGuardianrelation() {
        return this.guardianrelation;
    }
    
    public void setGuardianrelation(String guardianrelation) {
        this.guardianrelation = guardianrelation;
    }

    public String getGuardianaddress() {
        return this.guardianaddress;
    }
    
    public void setGuardianaddress(String guardianaddress) {
        this.guardianaddress = guardianaddress;
    }

    public String getCourtdate() {
        return this.courtdate;
    }
    
    public void setCourtdate(String courtdate) {
        this.courtdate = courtdate;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CustomerMasterLogId) ) return false;
		 CustomerMasterLogId castOther = ( CustomerMasterLogId ) other; 
         
		 return ( (this.getBrCode()==castOther.getBrCode()) || ( this.getBrCode()!=null && castOther.getBrCode()!=null && this.getBrCode().equals(castOther.getBrCode()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getCusttype()==castOther.getCusttype()) || ( this.getCusttype()!=null && castOther.getCusttype()!=null && this.getCusttype().equals(castOther.getCusttype()) ) )
 && ( (this.getIntroid()==castOther.getIntroid()) || ( this.getIntroid()!=null && castOther.getIntroid()!=null && this.getIntroid().equals(castOther.getIntroid()) ) )
 && ( (this.getFname()==castOther.getFname()) || ( this.getFname()!=null && castOther.getFname()!=null && this.getFname().equals(castOther.getFname()) ) )
 && ( (this.getMname()==castOther.getMname()) || ( this.getMname()!=null && castOther.getMname()!=null && this.getMname().equals(castOther.getMname()) ) )
 && ( (this.getLname()==castOther.getLname()) || ( this.getLname()!=null && castOther.getLname()!=null && this.getLname().equals(castOther.getLname()) ) )
 && ( (this.getSalute()==castOther.getSalute()) || ( this.getSalute()!=null && castOther.getSalute()!=null && this.getSalute().equals(castOther.getSalute()) ) )
 && ( (this.getMothername()==castOther.getMothername()) || ( this.getMothername()!=null && castOther.getMothername()!=null && this.getMothername().equals(castOther.getMothername()) ) )
 && ( (this.getFathername()==castOther.getFathername()) || ( this.getFathername()!=null && castOther.getFathername()!=null && this.getFathername().equals(castOther.getFathername()) ) )
 && ( (this.getHusbandname()==castOther.getHusbandname()) || ( this.getHusbandname()!=null && castOther.getHusbandname()!=null && this.getHusbandname().equals(castOther.getHusbandname()) ) )
 && ( (this.getDob()==castOther.getDob()) || ( this.getDob()!=null && castOther.getDob()!=null && this.getDob().equals(castOther.getDob()) ) )
 && ( (this.getSex()==castOther.getSex()) || ( this.getSex()!=null && castOther.getSex()!=null && this.getSex().equals(castOther.getSex()) ) )
 && ( (this.getMarital()==castOther.getMarital()) || ( this.getMarital()!=null && castOther.getMarital()!=null && this.getMarital().equals(castOther.getMarital()) ) )
 && ( (this.getSubCategory()==castOther.getSubCategory()) || ( this.getSubCategory()!=null && castOther.getSubCategory()!=null && this.getSubCategory().equals(castOther.getSubCategory()) ) )
 && ( (this.getPhoto()==castOther.getPhoto()) || ( this.getPhoto()!=null && castOther.getPhoto()!=null && this.getPhoto().equals(castOther.getPhoto()) ) )
 && ( (this.getSign()==castOther.getSign()) || ( this.getSign()!=null && castOther.getSign()!=null && this.getSign().equals(castOther.getSign()) ) )
 && ( (this.getNameproof()==castOther.getNameproof()) || ( this.getNameproof()!=null && castOther.getNameproof()!=null && this.getNameproof().equals(castOther.getNameproof()) ) )
 && ( (this.getAddrproof()==castOther.getAddrproof()) || ( this.getAddrproof()!=null && castOther.getAddrproof()!=null && this.getAddrproof().equals(castOther.getAddrproof()) ) )
 && ( (this.getPanno()==castOther.getPanno()) || ( this.getPanno()!=null && castOther.getPanno()!=null && this.getPanno().equals(castOther.getPanno()) ) )
 && ( (this.getOccupation()==castOther.getOccupation()) || ( this.getOccupation()!=null && castOther.getOccupation()!=null && this.getOccupation().equals(castOther.getOccupation()) ) )
 && ( (this.getSuboccupation()==castOther.getSuboccupation()) || ( this.getSuboccupation()!=null && castOther.getSuboccupation()!=null && this.getSuboccupation().equals(castOther.getSuboccupation()) ) )
 && ( (this.getScst()==castOther.getScst()) || ( this.getScst()!=null && castOther.getScst()!=null && this.getScst().equals(castOther.getScst()) ) )
 && ( (this.getCaste()==castOther.getCaste()) || ( this.getCaste()!=null && castOther.getCaste()!=null && this.getCaste().equals(castOther.getCaste()) ) )
 && ( (this.getNationality()==castOther.getNationality()) || ( this.getNationality()!=null && castOther.getNationality()!=null && this.getNationality().equals(castOther.getNationality()) ) )
 && ( (this.getReligion()==castOther.getReligion()) || ( this.getReligion()!=null && castOther.getReligion()!=null && this.getReligion().equals(castOther.getReligion()) ) )
 && ( (this.getPassportno()==castOther.getPassportno()) || ( this.getPassportno()!=null && castOther.getPassportno()!=null && this.getPassportno().equals(castOther.getPassportno()) ) )
 && ( (this.getDateofissue()==castOther.getDateofissue()) || ( this.getDateofissue()!=null && castOther.getDateofissue()!=null && this.getDateofissue().equals(castOther.getDateofissue()) ) )
 && ( (this.getDateofexpiry()==castOther.getDateofexpiry()) || ( this.getDateofexpiry()!=null && castOther.getDateofexpiry()!=null && this.getDateofexpiry().equals(castOther.getDateofexpiry()) ) )
 && ( (this.getGuardiantype()==castOther.getGuardiantype()) || ( this.getGuardiantype()!=null && castOther.getGuardiantype()!=null && this.getGuardiantype().equals(castOther.getGuardiantype()) ) )
 && ( (this.getGuardianname()==castOther.getGuardianname()) || ( this.getGuardianname()!=null && castOther.getGuardianname()!=null && this.getGuardianname().equals(castOther.getGuardianname()) ) )
 && ( (this.getGuardiansalute()==castOther.getGuardiansalute()) || ( this.getGuardiansalute()!=null && castOther.getGuardiansalute()!=null && this.getGuardiansalute().equals(castOther.getGuardiansalute()) ) )
 && ( (this.getGuardiansex()==castOther.getGuardiansex()) || ( this.getGuardiansex()!=null && castOther.getGuardiansex()!=null && this.getGuardiansex().equals(castOther.getGuardiansex()) ) )
 && ( (this.getGuardianrelation()==castOther.getGuardianrelation()) || ( this.getGuardianrelation()!=null && castOther.getGuardianrelation()!=null && this.getGuardianrelation().equals(castOther.getGuardianrelation()) ) )
 && ( (this.getGuardianaddress()==castOther.getGuardianaddress()) || ( this.getGuardianaddress()!=null && castOther.getGuardianaddress()!=null && this.getGuardianaddress().equals(castOther.getGuardianaddress()) ) )
 && ( (this.getCourtdate()==castOther.getCourtdate()) || ( this.getCourtdate()!=null && castOther.getCourtdate()!=null && this.getCourtdate().equals(castOther.getCourtdate()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBrCode() == null ? 0 : this.getBrCode().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getCusttype() == null ? 0 : this.getCusttype().hashCode() );
         result = 37 * result + ( getIntroid() == null ? 0 : this.getIntroid().hashCode() );
         result = 37 * result + ( getFname() == null ? 0 : this.getFname().hashCode() );
         result = 37 * result + ( getMname() == null ? 0 : this.getMname().hashCode() );
         result = 37 * result + ( getLname() == null ? 0 : this.getLname().hashCode() );
         result = 37 * result + ( getSalute() == null ? 0 : this.getSalute().hashCode() );
         result = 37 * result + ( getMothername() == null ? 0 : this.getMothername().hashCode() );
         result = 37 * result + ( getFathername() == null ? 0 : this.getFathername().hashCode() );
         result = 37 * result + ( getHusbandname() == null ? 0 : this.getHusbandname().hashCode() );
         result = 37 * result + ( getDob() == null ? 0 : this.getDob().hashCode() );
         result = 37 * result + ( getSex() == null ? 0 : this.getSex().hashCode() );
         result = 37 * result + ( getMarital() == null ? 0 : this.getMarital().hashCode() );
         result = 37 * result + ( getSubCategory() == null ? 0 : this.getSubCategory().hashCode() );
         result = 37 * result + ( getPhoto() == null ? 0 : this.getPhoto().hashCode() );
         result = 37 * result + ( getSign() == null ? 0 : this.getSign().hashCode() );
         result = 37 * result + ( getNameproof() == null ? 0 : this.getNameproof().hashCode() );
         result = 37 * result + ( getAddrproof() == null ? 0 : this.getAddrproof().hashCode() );
         result = 37 * result + ( getPanno() == null ? 0 : this.getPanno().hashCode() );
         result = 37 * result + ( getOccupation() == null ? 0 : this.getOccupation().hashCode() );
         result = 37 * result + ( getSuboccupation() == null ? 0 : this.getSuboccupation().hashCode() );
         result = 37 * result + ( getScst() == null ? 0 : this.getScst().hashCode() );
         result = 37 * result + ( getCaste() == null ? 0 : this.getCaste().hashCode() );
         result = 37 * result + ( getNationality() == null ? 0 : this.getNationality().hashCode() );
         result = 37 * result + ( getReligion() == null ? 0 : this.getReligion().hashCode() );
         result = 37 * result + ( getPassportno() == null ? 0 : this.getPassportno().hashCode() );
         result = 37 * result + ( getDateofissue() == null ? 0 : this.getDateofissue().hashCode() );
         result = 37 * result + ( getDateofexpiry() == null ? 0 : this.getDateofexpiry().hashCode() );
         result = 37 * result + ( getGuardiantype() == null ? 0 : this.getGuardiantype().hashCode() );
         result = 37 * result + ( getGuardianname() == null ? 0 : this.getGuardianname().hashCode() );
         result = 37 * result + ( getGuardiansalute() == null ? 0 : this.getGuardiansalute().hashCode() );
         result = 37 * result + ( getGuardiansex() == null ? 0 : this.getGuardiansex().hashCode() );
         result = 37 * result + ( getGuardianrelation() == null ? 0 : this.getGuardianrelation().hashCode() );
         result = 37 * result + ( getGuardianaddress() == null ? 0 : this.getGuardianaddress().hashCode() );
         result = 37 * result + ( getCourtdate() == null ? 0 : this.getCourtdate().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}