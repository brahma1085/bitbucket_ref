package com.banking.data.pojo;
// default package

import java.sql.Timestamp;


/**
 * MonthlyStatusId entity. @author MyEclipse Persistence Tools
 */

public class MonthlyStatusId  implements java.io.Serializable {


    // Fields    

     private Integer yrMth;
     private String postInd;
     private String monthClose;
     private String deTml;
     private String deUser;
     private Timestamp deDateTime;


    // Constructors

    /** default constructor */
    public MonthlyStatusId() {
    }

    
    /** full constructor */
    public MonthlyStatusId(Integer yrMth, String postInd, String monthClose, String deTml, String deUser, Timestamp deDateTime) {
        this.yrMth = yrMth;
        this.postInd = postInd;
        this.monthClose = monthClose;
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

    public String getMonthClose() {
        return this.monthClose;
    }
    
    public void setMonthClose(String monthClose) {
        this.monthClose = monthClose;
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
		 if ( !(other instanceof MonthlyStatusId) ) return false;
		 MonthlyStatusId castOther = ( MonthlyStatusId ) other; 
         
		 return ( (this.getYrMth()==castOther.getYrMth()) || ( this.getYrMth()!=null && castOther.getYrMth()!=null && this.getYrMth().equals(castOther.getYrMth()) ) )
 && ( (this.getPostInd()==castOther.getPostInd()) || ( this.getPostInd()!=null && castOther.getPostInd()!=null && this.getPostInd().equals(castOther.getPostInd()) ) )
 && ( (this.getMonthClose()==castOther.getMonthClose()) || ( this.getMonthClose()!=null && castOther.getMonthClose()!=null && this.getMonthClose().equals(castOther.getMonthClose()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDateTime()==castOther.getDeDateTime()) || ( this.getDeDateTime()!=null && castOther.getDeDateTime()!=null && this.getDeDateTime().equals(castOther.getDeDateTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getYrMth() == null ? 0 : this.getYrMth().hashCode() );
         result = 37 * result + ( getPostInd() == null ? 0 : this.getPostInd().hashCode() );
         result = 37 * result + ( getMonthClose() == null ? 0 : this.getMonthClose().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDateTime() == null ? 0 : this.getDeDateTime().hashCode() );
         return result;
   }   





}