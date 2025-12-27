package com.banking.data.pojo;
// default package



/**
 * LoanDocumentsId entity. @author MyEclipse Persistence Tools
 */

public class LoanDocumentsId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private Integer docCode;
     private String othDtls;
     private String trnKey;
     private String pledgedDate;
     private String returnDate;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public LoanDocumentsId() {
    }

    
    /** full constructor */
    public LoanDocumentsId(String acType, Integer acNo, Integer docCode, String othDtls, String trnKey, String pledgedDate, String returnDate, String deTml, String deUser, String deDate) {
        this.acType = acType;
        this.acNo = acNo;
        this.docCode = docCode;
        this.othDtls = othDtls;
        this.trnKey = trnKey;
        this.pledgedDate = pledgedDate;
        this.returnDate = returnDate;
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

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public Integer getDocCode() {
        return this.docCode;
    }
    
    public void setDocCode(Integer docCode) {
        this.docCode = docCode;
    }

    public String getOthDtls() {
        return this.othDtls;
    }
    
    public void setOthDtls(String othDtls) {
        this.othDtls = othDtls;
    }

    public String getTrnKey() {
        return this.trnKey;
    }
    
    public void setTrnKey(String trnKey) {
        this.trnKey = trnKey;
    }

    public String getPledgedDate() {
        return this.pledgedDate;
    }
    
    public void setPledgedDate(String pledgedDate) {
        this.pledgedDate = pledgedDate;
    }

    public String getReturnDate() {
        return this.returnDate;
    }
    
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
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
		 if ( !(other instanceof LoanDocumentsId) ) return false;
		 LoanDocumentsId castOther = ( LoanDocumentsId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getDocCode()==castOther.getDocCode()) || ( this.getDocCode()!=null && castOther.getDocCode()!=null && this.getDocCode().equals(castOther.getDocCode()) ) )
 && ( (this.getOthDtls()==castOther.getOthDtls()) || ( this.getOthDtls()!=null && castOther.getOthDtls()!=null && this.getOthDtls().equals(castOther.getOthDtls()) ) )
 && ( (this.getTrnKey()==castOther.getTrnKey()) || ( this.getTrnKey()!=null && castOther.getTrnKey()!=null && this.getTrnKey().equals(castOther.getTrnKey()) ) )
 && ( (this.getPledgedDate()==castOther.getPledgedDate()) || ( this.getPledgedDate()!=null && castOther.getPledgedDate()!=null && this.getPledgedDate().equals(castOther.getPledgedDate()) ) )
 && ( (this.getReturnDate()==castOther.getReturnDate()) || ( this.getReturnDate()!=null && castOther.getReturnDate()!=null && this.getReturnDate().equals(castOther.getReturnDate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getDocCode() == null ? 0 : this.getDocCode().hashCode() );
         result = 37 * result + ( getOthDtls() == null ? 0 : this.getOthDtls().hashCode() );
         result = 37 * result + ( getTrnKey() == null ? 0 : this.getTrnKey().hashCode() );
         result = 37 * result + ( getPledgedDate() == null ? 0 : this.getPledgedDate().hashCode() );
         result = 37 * result + ( getReturnDate() == null ? 0 : this.getReturnDate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}