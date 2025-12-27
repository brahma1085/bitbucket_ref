package com.banking.data.pojo;
// default package



/**
 * StageMasterId entity. @author MyEclipse Persistence Tools
 */

public class StageMasterId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer stageCd;
     private String descptn;
     private String stageTy;
     private Integer prdFrom;
     private Integer prdTo;
     private String reversbl;
     private String toHist;
     private String splrt;
     private Integer noOfAcs;
     private Double totAmt;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public StageMasterId() {
    }

    
    /** full constructor */
    public StageMasterId(String acType, Integer stageCd, String descptn, String stageTy, Integer prdFrom, Integer prdTo, String reversbl, String toHist, String splrt, Integer noOfAcs, Double totAmt, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.stageCd = stageCd;
        this.descptn = descptn;
        this.stageTy = stageTy;
        this.prdFrom = prdFrom;
        this.prdTo = prdTo;
        this.reversbl = reversbl;
        this.toHist = toHist;
        this.splrt = splrt;
        this.noOfAcs = noOfAcs;
        this.totAmt = totAmt;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getStageCd() {
        return this.stageCd;
    }
    
    public void setStageCd(Integer stageCd) {
        this.stageCd = stageCd;
    }

    public String getDescptn() {
        return this.descptn;
    }
    
    public void setDescptn(String descptn) {
        this.descptn = descptn;
    }

    public String getStageTy() {
        return this.stageTy;
    }
    
    public void setStageTy(String stageTy) {
        this.stageTy = stageTy;
    }

    public Integer getPrdFrom() {
        return this.prdFrom;
    }
    
    public void setPrdFrom(Integer prdFrom) {
        this.prdFrom = prdFrom;
    }

    public Integer getPrdTo() {
        return this.prdTo;
    }
    
    public void setPrdTo(Integer prdTo) {
        this.prdTo = prdTo;
    }

    public String getReversbl() {
        return this.reversbl;
    }
    
    public void setReversbl(String reversbl) {
        this.reversbl = reversbl;
    }

    public String getToHist() {
        return this.toHist;
    }
    
    public void setToHist(String toHist) {
        this.toHist = toHist;
    }

    public String getSplrt() {
        return this.splrt;
    }
    
    public void setSplrt(String splrt) {
        this.splrt = splrt;
    }

    public Integer getNoOfAcs() {
        return this.noOfAcs;
    }
    
    public void setNoOfAcs(Integer noOfAcs) {
        this.noOfAcs = noOfAcs;
    }

    public Double getTotAmt() {
        return this.totAmt;
    }
    
    public void setTotAmt(Double totAmt) {
        this.totAmt = totAmt;
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

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof StageMasterId) ) return false;
		 StageMasterId castOther = ( StageMasterId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getStageCd()==castOther.getStageCd()) || ( this.getStageCd()!=null && castOther.getStageCd()!=null && this.getStageCd().equals(castOther.getStageCd()) ) )
 && ( (this.getDescptn()==castOther.getDescptn()) || ( this.getDescptn()!=null && castOther.getDescptn()!=null && this.getDescptn().equals(castOther.getDescptn()) ) )
 && ( (this.getStageTy()==castOther.getStageTy()) || ( this.getStageTy()!=null && castOther.getStageTy()!=null && this.getStageTy().equals(castOther.getStageTy()) ) )
 && ( (this.getPrdFrom()==castOther.getPrdFrom()) || ( this.getPrdFrom()!=null && castOther.getPrdFrom()!=null && this.getPrdFrom().equals(castOther.getPrdFrom()) ) )
 && ( (this.getPrdTo()==castOther.getPrdTo()) || ( this.getPrdTo()!=null && castOther.getPrdTo()!=null && this.getPrdTo().equals(castOther.getPrdTo()) ) )
 && ( (this.getReversbl()==castOther.getReversbl()) || ( this.getReversbl()!=null && castOther.getReversbl()!=null && this.getReversbl().equals(castOther.getReversbl()) ) )
 && ( (this.getToHist()==castOther.getToHist()) || ( this.getToHist()!=null && castOther.getToHist()!=null && this.getToHist().equals(castOther.getToHist()) ) )
 && ( (this.getSplrt()==castOther.getSplrt()) || ( this.getSplrt()!=null && castOther.getSplrt()!=null && this.getSplrt().equals(castOther.getSplrt()) ) )
 && ( (this.getNoOfAcs()==castOther.getNoOfAcs()) || ( this.getNoOfAcs()!=null && castOther.getNoOfAcs()!=null && this.getNoOfAcs().equals(castOther.getNoOfAcs()) ) )
 && ( (this.getTotAmt()==castOther.getTotAmt()) || ( this.getTotAmt()!=null && castOther.getTotAmt()!=null && this.getTotAmt().equals(castOther.getTotAmt()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getStageCd() == null ? 0 : this.getStageCd().hashCode() );
         result = 37 * result + ( getDescptn() == null ? 0 : this.getDescptn().hashCode() );
         result = 37 * result + ( getStageTy() == null ? 0 : this.getStageTy().hashCode() );
         result = 37 * result + ( getPrdFrom() == null ? 0 : this.getPrdFrom().hashCode() );
         result = 37 * result + ( getPrdTo() == null ? 0 : this.getPrdTo().hashCode() );
         result = 37 * result + ( getReversbl() == null ? 0 : this.getReversbl().hashCode() );
         result = 37 * result + ( getToHist() == null ? 0 : this.getToHist().hashCode() );
         result = 37 * result + ( getSplrt() == null ? 0 : this.getSplrt().hashCode() );
         result = 37 * result + ( getNoOfAcs() == null ? 0 : this.getNoOfAcs().hashCode() );
         result = 37 * result + ( getTotAmt() == null ? 0 : this.getTotAmt().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}