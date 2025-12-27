package com.banking.data.pojo;
// default package



/**
 * DCBReportId entity. @author MyEclipse Persistence Tools
 */

public class DCBReportId  implements java.io.Serializable {


    // Fields    

     private String month;
     private String acType;
     private Integer acNo;
     private Double lnBal;
     private Double prArr;
     private Double intArr;
     private Double pintArr;
     private Double ochgArr;
     private Double advPaid;
     private Double prDmd;
     private Double intDmd;
     private Double ochgDmd;
     private Double prColl;
     private Double intColl;
     private Double pintColl;
     private Double ochgColl;
     private Double advColl;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public DCBReportId() {
    }

    
    /** full constructor */
    public DCBReportId(String month, String acType, Integer acNo, Double lnBal, Double prArr, Double intArr, Double pintArr, Double ochgArr, Double advPaid, Double prDmd, Double intDmd, Double ochgDmd, Double prColl, Double intColl, Double pintColl, Double ochgColl, Double advColl, String deTml, String deUser, String deDate) {
        this.month = month;
        this.acType = acType;
        this.acNo = acNo;
        this.lnBal = lnBal;
        this.prArr = prArr;
        this.intArr = intArr;
        this.pintArr = pintArr;
        this.ochgArr = ochgArr;
        this.advPaid = advPaid;
        this.prDmd = prDmd;
        this.intDmd = intDmd;
        this.ochgDmd = ochgDmd;
        this.prColl = prColl;
        this.intColl = intColl;
        this.pintColl = pintColl;
        this.ochgColl = ochgColl;
        this.advColl = advColl;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getMonth() {
        return this.month;
    }
    
    public void setMonth(String month) {
        this.month = month;
    }

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

    public Double getLnBal() {
        return this.lnBal;
    }
    
    public void setLnBal(Double lnBal) {
        this.lnBal = lnBal;
    }

    public Double getPrArr() {
        return this.prArr;
    }
    
    public void setPrArr(Double prArr) {
        this.prArr = prArr;
    }

    public Double getIntArr() {
        return this.intArr;
    }
    
    public void setIntArr(Double intArr) {
        this.intArr = intArr;
    }

    public Double getPintArr() {
        return this.pintArr;
    }
    
    public void setPintArr(Double pintArr) {
        this.pintArr = pintArr;
    }

    public Double getOchgArr() {
        return this.ochgArr;
    }
    
    public void setOchgArr(Double ochgArr) {
        this.ochgArr = ochgArr;
    }

    public Double getAdvPaid() {
        return this.advPaid;
    }
    
    public void setAdvPaid(Double advPaid) {
        this.advPaid = advPaid;
    }

    public Double getPrDmd() {
        return this.prDmd;
    }
    
    public void setPrDmd(Double prDmd) {
        this.prDmd = prDmd;
    }

    public Double getIntDmd() {
        return this.intDmd;
    }
    
    public void setIntDmd(Double intDmd) {
        this.intDmd = intDmd;
    }

    public Double getOchgDmd() {
        return this.ochgDmd;
    }
    
    public void setOchgDmd(Double ochgDmd) {
        this.ochgDmd = ochgDmd;
    }

    public Double getPrColl() {
        return this.prColl;
    }
    
    public void setPrColl(Double prColl) {
        this.prColl = prColl;
    }

    public Double getIntColl() {
        return this.intColl;
    }
    
    public void setIntColl(Double intColl) {
        this.intColl = intColl;
    }

    public Double getPintColl() {
        return this.pintColl;
    }
    
    public void setPintColl(Double pintColl) {
        this.pintColl = pintColl;
    }

    public Double getOchgColl() {
        return this.ochgColl;
    }
    
    public void setOchgColl(Double ochgColl) {
        this.ochgColl = ochgColl;
    }

    public Double getAdvColl() {
        return this.advColl;
    }
    
    public void setAdvColl(Double advColl) {
        this.advColl = advColl;
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
		 if ( !(other instanceof DCBReportId) ) return false;
		 DCBReportId castOther = ( DCBReportId ) other; 
         
		 return ( (this.getMonth()==castOther.getMonth()) || ( this.getMonth()!=null && castOther.getMonth()!=null && this.getMonth().equals(castOther.getMonth()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getLnBal()==castOther.getLnBal()) || ( this.getLnBal()!=null && castOther.getLnBal()!=null && this.getLnBal().equals(castOther.getLnBal()) ) )
 && ( (this.getPrArr()==castOther.getPrArr()) || ( this.getPrArr()!=null && castOther.getPrArr()!=null && this.getPrArr().equals(castOther.getPrArr()) ) )
 && ( (this.getIntArr()==castOther.getIntArr()) || ( this.getIntArr()!=null && castOther.getIntArr()!=null && this.getIntArr().equals(castOther.getIntArr()) ) )
 && ( (this.getPintArr()==castOther.getPintArr()) || ( this.getPintArr()!=null && castOther.getPintArr()!=null && this.getPintArr().equals(castOther.getPintArr()) ) )
 && ( (this.getOchgArr()==castOther.getOchgArr()) || ( this.getOchgArr()!=null && castOther.getOchgArr()!=null && this.getOchgArr().equals(castOther.getOchgArr()) ) )
 && ( (this.getAdvPaid()==castOther.getAdvPaid()) || ( this.getAdvPaid()!=null && castOther.getAdvPaid()!=null && this.getAdvPaid().equals(castOther.getAdvPaid()) ) )
 && ( (this.getPrDmd()==castOther.getPrDmd()) || ( this.getPrDmd()!=null && castOther.getPrDmd()!=null && this.getPrDmd().equals(castOther.getPrDmd()) ) )
 && ( (this.getIntDmd()==castOther.getIntDmd()) || ( this.getIntDmd()!=null && castOther.getIntDmd()!=null && this.getIntDmd().equals(castOther.getIntDmd()) ) )
 && ( (this.getOchgDmd()==castOther.getOchgDmd()) || ( this.getOchgDmd()!=null && castOther.getOchgDmd()!=null && this.getOchgDmd().equals(castOther.getOchgDmd()) ) )
 && ( (this.getPrColl()==castOther.getPrColl()) || ( this.getPrColl()!=null && castOther.getPrColl()!=null && this.getPrColl().equals(castOther.getPrColl()) ) )
 && ( (this.getIntColl()==castOther.getIntColl()) || ( this.getIntColl()!=null && castOther.getIntColl()!=null && this.getIntColl().equals(castOther.getIntColl()) ) )
 && ( (this.getPintColl()==castOther.getPintColl()) || ( this.getPintColl()!=null && castOther.getPintColl()!=null && this.getPintColl().equals(castOther.getPintColl()) ) )
 && ( (this.getOchgColl()==castOther.getOchgColl()) || ( this.getOchgColl()!=null && castOther.getOchgColl()!=null && this.getOchgColl().equals(castOther.getOchgColl()) ) )
 && ( (this.getAdvColl()==castOther.getAdvColl()) || ( this.getAdvColl()!=null && castOther.getAdvColl()!=null && this.getAdvColl().equals(castOther.getAdvColl()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMonth() == null ? 0 : this.getMonth().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getLnBal() == null ? 0 : this.getLnBal().hashCode() );
         result = 37 * result + ( getPrArr() == null ? 0 : this.getPrArr().hashCode() );
         result = 37 * result + ( getIntArr() == null ? 0 : this.getIntArr().hashCode() );
         result = 37 * result + ( getPintArr() == null ? 0 : this.getPintArr().hashCode() );
         result = 37 * result + ( getOchgArr() == null ? 0 : this.getOchgArr().hashCode() );
         result = 37 * result + ( getAdvPaid() == null ? 0 : this.getAdvPaid().hashCode() );
         result = 37 * result + ( getPrDmd() == null ? 0 : this.getPrDmd().hashCode() );
         result = 37 * result + ( getIntDmd() == null ? 0 : this.getIntDmd().hashCode() );
         result = 37 * result + ( getOchgDmd() == null ? 0 : this.getOchgDmd().hashCode() );
         result = 37 * result + ( getPrColl() == null ? 0 : this.getPrColl().hashCode() );
         result = 37 * result + ( getIntColl() == null ? 0 : this.getIntColl().hashCode() );
         result = 37 * result + ( getPintColl() == null ? 0 : this.getPintColl().hashCode() );
         result = 37 * result + ( getOchgColl() == null ? 0 : this.getOchgColl().hashCode() );
         result = 37 * result + ( getAdvColl() == null ? 0 : this.getAdvColl().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}