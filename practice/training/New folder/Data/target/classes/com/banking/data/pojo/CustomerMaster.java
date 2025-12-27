package com.banking.data.pojo;
// default package



/**
 * CustomerMaster entity. @author MyEclipse Persistence Tools
 */

public class CustomerMaster  implements java.io.Serializable {


    // Fields    

     private Integer cid;
     private Integer brCode;
     private Integer custtype;
     private Integer introid;
     private String fname;
     private String mname;
     private String lname;
     private String salute;
     private String mothername;
     private String fathername;
     private String spousename;
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
    public CustomerMaster() {
    }

    
    /** full constructor */
    public CustomerMaster(Integer brCode, Integer custtype, Integer introid, String fname, String mname, String lname, String salute, String mothername, String fathername, String spousename, String dob, String sex, String marital, Integer subCategory, String photo, String sign, String nameproof, String addrproof, String panno, String occupation, String suboccupation, String scst, String caste, String nationality, String religion, String passportno, String dateofissue, String dateofexpiry, String guardiantype, String guardianname, Integer guardiansalute, String guardiansex, String guardianrelation, String guardianaddress, String courtdate, String deUser, String deTml, String deDate, String veUser, String veTml, String veDate) {
        this.brCode = brCode;
        this.custtype = custtype;
        this.introid = introid;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.salute = salute;
        this.mothername = mothername;
        this.fathername = fathername;
        this.spousename = spousename;
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

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getBrCode() {
        return this.brCode;
    }
    
    public void setBrCode(Integer brCode) {
        this.brCode = brCode;
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

    public String getSpousename() {
        return this.spousename;
    }
    
    public void setSpousename(String spousename) {
        this.spousename = spousename;
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
   








}