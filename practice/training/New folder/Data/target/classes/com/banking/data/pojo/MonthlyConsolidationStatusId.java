package com.banking.data.pojo;
// default package

import java.sql.Timestamp;


/**
 * MonthlyConsolidationStatusId entity. @author MyEclipse Persistence Tools
 */

public class MonthlyConsolidationStatusId  implements java.io.Serializable {


    // Fields    

     private Integer yrMth;
     private String postInd;
     private Integer brCode;
     private String deTml;
     private String deUser;
     private Timestamp deDateTime;


    // Constructors

    /** default constructor */
    public MonthlyConsolidationStatusId() {
    }

    
    /** full constructor */
    public MonthlyConsolidationStatusId(Integer yrMth, String postInd, Integer brCode, String deTml, String deUser, Timestamp deDateTime) {
        this.yrMth = yrMth;
        this.postInd = postInd;
        this.brCode = brCode;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDateTime = deDateTime;
    }

   
    // Property accessors

    public Integer getYrMth() {
        return this.yrMth;
    }
    
    public void setYrMth(Integer yrMth) {
        this.yrMth = yrMth;
    }

    public String getPostInd() {
        return this.postInd;
    }
    
    public void setPostInd(String postInd) {
        this.postInd = postInd;
    }

    public Integer getBrCode() {
        return this.brCode;
    }
    
    public void setBrCode(Integer brCode) {
        this.brCode = brCode;
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

    public Timestamp getDeDateTime() {
        return this.deDateTime;
    }
    
    public void setDeDateTime(Timestamp deDateTime) {
        this.deDateTime = deDateTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MonthlyConsolidationStatusId) ) return false;
		 MonthlyConsolidationStatusId castOther = ( MonthlyConsolidationStatusId ) other; 
         
		 return ( (this.getYrMth()==castOther.getYrMth()) || ( this.getYrMth()!=null && castOther.getYrMth()!=null && this.getYrMth().equals(castOther.getYrMth()) ) )
 && ( (this.getPostInd()==castOther.getPostInd()) || ( this.getPostInd()!=null && castOther.getPostInd()!=null && this.getPostInd().equals(castOther.getPostInd()) ) )
 && ( (this.getBrCode()==castOther.getBrCode()) || ( this.getBrCode()!=null && castOther.getBrCode()!=null && this.getBrCode().equals(castOther.getBrCode()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDateTime()==castOther.getDeDateTime()) || ( this.getDeDateTime()!=null && castOther.getDeDateTime()!=null && this.getDeDateTime().equals(castOther.getDeDateTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getYrMth() == null ? 0 : this.getYrMth().hashCode() );
         result = 37 * result + ( getPostInd() == null ? 0 : this.getPostInd().hashCode() );
         result = 37 * result + ( getBrCode() == null ? 0 : this.getBrCode().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDateTime() == null ? 0 : this.getDeDateTime().hashCode() );
         return result;
   }   





}