package com.banking.data.pojo;
// default package

import java.util.Date;


/**
 * TokenNumbersId entity. @author MyEclipse Persistence Tools
 */

public class TokenNumbersId  implements java.io.Serializable {


    // Fields    

     private Integer tokenNo;
     private Date fromDate;
     private Date toDate;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public TokenNumbersId() {
    }

    
    /** full constructor */
    public TokenNumbersId(Integer tokenNo, Date fromDate, Date toDate, String deTml, String deUser, String deDate) {
        this.tokenNo = tokenNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getTokenNo() {
        return this.tokenNo;
    }
    
    public void setTokenNo(Integer tokenNo) {
        this.tokenNo = tokenNo;
    }

    public Date getFromDate() {
        return this.fromDate;
    }
    
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return this.toDate;
    }
    
    public void setToDate(Date toDate) {
        this.toDate = toDate;
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
		 if ( !(other instanceof TokenNumbersId) ) return false;
		 TokenNumbersId castOther = ( TokenNumbersId ) other; 
         
		 return ( (this.getTokenNo()==castOther.getTokenNo()) || ( this.getTokenNo()!=null && castOther.getTokenNo()!=null && this.getTokenNo().equals(castOther.getTokenNo()) ) )
 && ( (this.getFromDate()==castOther.getFromDate()) || ( this.getFromDate()!=null && castOther.getFromDate()!=null && this.getFromDate().equals(castOther.getFromDate()) ) )
 && ( (this.getToDate()==castOther.getToDate()) || ( this.getToDate()!=null && castOther.getToDate()!=null && this.getToDate().equals(castOther.getToDate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTokenNo() == null ? 0 : this.getTokenNo().hashCode() );
         result = 37 * result + ( getFromDate() == null ? 0 : this.getFromDate().hashCode() );
         result = 37 * result + ( getToDate() == null ? 0 : this.getToDate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}