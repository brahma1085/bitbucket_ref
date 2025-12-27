package com.banking.data.pojo;
// default package



/**
 * ChequeBookId entity. @author MyEclipse Persistence Tools
 */

public class ChequeBookId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String requestDt;
     private Integer FChqPrev;
     private Integer bookNo;
     private Integer fstChqNo;
     private Integer lstChqNo;
     private Integer noLeaf;
     private Integer noBounce;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public ChequeBookId() {
    }

    
    /** full constructor */
    public ChequeBookId(String acType, Integer acNo, String requestDt, Integer FChqPrev, Integer bookNo, Integer fstChqNo, Integer lstChqNo, Integer noLeaf, Integer noBounce, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.requestDt = requestDt;
        this.FChqPrev = FChqPrev;
        this.bookNo = bookNo;
        this.fstChqNo = fstChqNo;
        this.lstChqNo = lstChqNo;
        this.noLeaf = noLeaf;
        this.noBounce = noBounce;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
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

    public String getRequestDt() {
        return this.requestDt;
    }
    
    public void setRequestDt(String requestDt) {
        this.requestDt = requestDt;
    }

    public Integer getFChqPrev() {
        return this.FChqPrev;
    }
    
    public void setFChqPrev(Integer FChqPrev) {
        this.FChqPrev = FChqPrev;
    }

    public Integer getBookNo() {
        return this.bookNo;
    }
    
    public void setBookNo(Integer bookNo) {
        this.bookNo = bookNo;
    }

    public Integer getFstChqNo() {
        return this.fstChqNo;
    }
    
    public void setFstChqNo(Integer fstChqNo) {
        this.fstChqNo = fstChqNo;
    }

    public Integer getLstChqNo() {
        return this.lstChqNo;
    }
    
    public void setLstChqNo(Integer lstChqNo) {
        this.lstChqNo = lstChqNo;
    }

    public Integer getNoLeaf() {
        return this.noLeaf;
    }
    
    public void setNoLeaf(Integer noLeaf) {
        this.noLeaf = noLeaf;
    }

    public Integer getNoBounce() {
        return this.noBounce;
    }
    
    public void setNoBounce(Integer noBounce) {
        this.noBounce = noBounce;
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

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
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
		 if ( !(other instanceof ChequeBookId) ) return false;
		 ChequeBookId castOther = ( ChequeBookId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getRequestDt()==castOther.getRequestDt()) || ( this.getRequestDt()!=null && castOther.getRequestDt()!=null && this.getRequestDt().equals(castOther.getRequestDt()) ) )
 && ( (this.getFChqPrev()==castOther.getFChqPrev()) || ( this.getFChqPrev()!=null && castOther.getFChqPrev()!=null && this.getFChqPrev().equals(castOther.getFChqPrev()) ) )
 && ( (this.getBookNo()==castOther.getBookNo()) || ( this.getBookNo()!=null && castOther.getBookNo()!=null && this.getBookNo().equals(castOther.getBookNo()) ) )
 && ( (this.getFstChqNo()==castOther.getFstChqNo()) || ( this.getFstChqNo()!=null && castOther.getFstChqNo()!=null && this.getFstChqNo().equals(castOther.getFstChqNo()) ) )
 && ( (this.getLstChqNo()==castOther.getLstChqNo()) || ( this.getLstChqNo()!=null && castOther.getLstChqNo()!=null && this.getLstChqNo().equals(castOther.getLstChqNo()) ) )
 && ( (this.getNoLeaf()==castOther.getNoLeaf()) || ( this.getNoLeaf()!=null && castOther.getNoLeaf()!=null && this.getNoLeaf().equals(castOther.getNoLeaf()) ) )
 && ( (this.getNoBounce()==castOther.getNoBounce()) || ( this.getNoBounce()!=null && castOther.getNoBounce()!=null && this.getNoBounce().equals(castOther.getNoBounce()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getRequestDt() == null ? 0 : this.getRequestDt().hashCode() );
         result = 37 * result + ( getFChqPrev() == null ? 0 : this.getFChqPrev().hashCode() );
         result = 37 * result + ( getBookNo() == null ? 0 : this.getBookNo().hashCode() );
         result = 37 * result + ( getFstChqNo() == null ? 0 : this.getFstChqNo().hashCode() );
         result = 37 * result + ( getLstChqNo() == null ? 0 : this.getLstChqNo().hashCode() );
         result = 37 * result + ( getNoLeaf() == null ? 0 : this.getNoLeaf().hashCode() );
         result = 37 * result + ( getNoBounce() == null ? 0 : this.getNoBounce().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}