package com.banking.data.pojo;
// default package



/**
 * PSWSDetailsId entity. @author MyEclipse Persistence Tools
 */

public class PSWSDetailsId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer prCode;
     private Double sancAmt;
     private Double advancedAmt;
     private Double balOutstding;
     private Double amtOverdue;
     private String category;
     private String wkSect;
     private String sexCd;
     private String processDt;
     private String deTml;
     private String deUser;
     private String deDatetime;


    // Constructors

    /** default constructor */
    public PSWSDetailsId() {
    }

    
    /** full constructor */
    public PSWSDetailsId(String acType, Integer acNo, Integer prCode, Double sancAmt, Double advancedAmt, Double balOutstding, Double amtOverdue, String category, String wkSect, String sexCd, String processDt, String deTml, String deUser, String deDatetime) {
        this.acType = acType;
        this.acNo = acNo;
        this.prCode = prCode;
        this.sancAmt = sancAmt;
        this.advancedAmt = advancedAmt;
        this.balOutstding = balOutstding;
        this.amtOverdue = amtOverdue;
        this.category = category;
        this.wkSect = wkSect;
        this.sexCd = sexCd;
        this.processDt = processDt;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDatetime = deDatetime;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public Integer getPrCode() {
        return this.prCode;
    }
    
    public void setPrCode(Integer prCode) {
        this.prCode = prCode;
    }

    public Double getSancAmt() {
        return this.sancAmt;
    }
    
    public void setSancAmt(Double sancAmt) {
        this.sancAmt = sancAmt;
    }

    public Double getAdvancedAmt() {
        return this.advancedAmt;
    }
    
    public void setAdvancedAmt(Double advancedAmt) {
        this.advancedAmt = advancedAmt;
    }

    public Double getBalOutstding() {
        return this.balOutstding;
    }
    
    public void setBalOutstding(Double balOutstding) {
        this.balOutstding = balOutstding;
    }

    public Double getAmtOverdue() {
        return this.amtOverdue;
    }
    
    public void setAmtOverdue(Double amtOverdue) {
        this.amtOverdue = amtOverdue;
    }

    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public String getWkSect() {
        return this.wkSect;
    }
    
    public void setWkSect(String wkSect) {
        this.wkSect = wkSect;
    }

    public String getSexCd() {
        return this.sexCd;
    }
    
    public void setSexCd(String sexCd) {
        this.sexCd = sexCd;
    }

    public String getProcessDt() {
        return this.processDt;
    }
    
    public void setProcessDt(String processDt) {
        this.processDt = processDt;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeDatetime() {
        return this.deDatetime;
    }
    
    public void setDeDatetime(String deDatetime) {
        this.deDatetime = deDatetime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PSWSDetailsId) ) return false;
		 PSWSDetailsId castOther = ( PSWSDetailsId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getPrCode()==castOther.getPrCode()) || ( this.getPrCode()!=null && castOther.getPrCode()!=null && this.getPrCode().equals(castOther.getPrCode()) ) )
 && ( (this.getSancAmt()==castOther.getSancAmt()) || ( this.getSancAmt()!=null && castOther.getSancAmt()!=null && this.getSancAmt().equals(castOther.getSancAmt()) ) )
 && ( (this.getAdvancedAmt()==castOther.getAdvancedAmt()) || ( this.getAdvancedAmt()!=null && castOther.getAdvancedAmt()!=null && this.getAdvancedAmt().equals(castOther.getAdvancedAmt()) ) )
 && ( (this.getBalOutstding()==castOther.getBalOutstding()) || ( this.getBalOutstding()!=null && castOther.getBalOutstding()!=null && this.getBalOutstding().equals(castOther.getBalOutstding()) ) )
 && ( (this.getAmtOverdue()==castOther.getAmtOverdue()) || ( this.getAmtOverdue()!=null && castOther.getAmtOverdue()!=null && this.getAmtOverdue().equals(castOther.getAmtOverdue()) ) )
 && ( (this.getCategory()==castOther.getCategory()) || ( this.getCategory()!=null && castOther.getCategory()!=null && this.getCategory().equals(castOther.getCategory()) ) )
 && ( (this.getWkSect()==castOther.getWkSect()) || ( this.getWkSect()!=null && castOther.getWkSect()!=null && this.getWkSect().equals(castOther.getWkSect()) ) )
 && ( (this.getSexCd()==castOther.getSexCd()) || ( this.getSexCd()!=null && castOther.getSexCd()!=null && this.getSexCd().equals(castOther.getSexCd()) ) )
 && ( (this.getProcessDt()==castOther.getProcessDt()) || ( this.getProcessDt()!=null && castOther.getProcessDt()!=null && this.getProcessDt().equals(castOther.getProcessDt()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDatetime()==castOther.getDeDatetime()) || ( this.getDeDatetime()!=null && castOther.getDeDatetime()!=null && this.getDeDatetime().equals(castOther.getDeDatetime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getPrCode() == null ? 0 : this.getPrCode().hashCode() );
         result = 37 * result + ( getSancAmt() == null ? 0 : this.getSancAmt().hashCode() );
         result = 37 * result + ( getAdvancedAmt() == null ? 0 : this.getAdvancedAmt().hashCode() );
         result = 37 * result + ( getBalOutstding() == null ? 0 : this.getBalOutstding().hashCode() );
         result = 37 * result + ( getAmtOverdue() == null ? 0 : this.getAmtOverdue().hashCode() );
         result = 37 * result + ( getCategory() == null ? 0 : this.getCategory().hashCode() );
         result = 37 * result + ( getWkSect() == null ? 0 : this.getWkSect().hashCode() );
         result = 37 * result + ( getSexCd() == null ? 0 : this.getSexCd().hashCode() );
         result = 37 * result + ( getProcessDt() == null ? 0 : this.getProcessDt().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDatetime() == null ? 0 : this.getDeDatetime().hashCode() );
         return result;
   }   





}