package com.banking.data.pojo;
// default package



/**
 * QuarterDefinitionId entity. @author MyEclipse Persistence Tools
 */

public class QuarterDefinitionId  implements java.io.Serializable {


    // Fields    

     private Integer month;
     private String qtrDefn;
     private String hyrDefn;
     private String yrDefn;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public QuarterDefinitionId() {
    }

    
    /** full constructor */
    public QuarterDefinitionId(Integer month, String qtrDefn, String hyrDefn, String yrDefn, String deTml, String deUser, String deDate) {
        this.month = month;
        this.qtrDefn = qtrDefn;
        this.hyrDefn = hyrDefn;
        this.yrDefn = yrDefn;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getMonth() {
        return this.month;
    }
    
    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getQtrDefn() {
        return this.qtrDefn;
    }
    
    public void setQtrDefn(String qtrDefn) {
        this.qtrDefn = qtrDefn;
    }

    public String getHyrDefn() {
        return this.hyrDefn;
    }
    
    public void setHyrDefn(String hyrDefn) {
        this.hyrDefn = hyrDefn;
    }

    public String getYrDefn() {
        return this.yrDefn;
    }
    
    public void setYrDefn(String yrDefn) {
        this.yrDefn = yrDefn;
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
		 if ( !(other instanceof QuarterDefinitionId) ) return false;
		 QuarterDefinitionId castOther = ( QuarterDefinitionId ) other; 
         
		 return ( (this.getMonth()==castOther.getMonth()) || ( this.getMonth()!=null && castOther.getMonth()!=null && this.getMonth().equals(castOther.getMonth()) ) )
 && ( (this.getQtrDefn()==castOther.getQtrDefn()) || ( this.getQtrDefn()!=null && castOther.getQtrDefn()!=null && this.getQtrDefn().equals(castOther.getQtrDefn()) ) )
 && ( (this.getHyrDefn()==castOther.getHyrDefn()) || ( this.getHyrDefn()!=null && castOther.getHyrDefn()!=null && this.getHyrDefn().equals(castOther.getHyrDefn()) ) )
 && ( (this.getYrDefn()==castOther.getYrDefn()) || ( this.getYrDefn()!=null && castOther.getYrDefn()!=null && this.getYrDefn().equals(castOther.getYrDefn()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMonth() == null ? 0 : this.getMonth().hashCode() );
         result = 37 * result + ( getQtrDefn() == null ? 0 : this.getQtrDefn().hashCode() );
         result = 37 * result + ( getHyrDefn() == null ? 0 : this.getHyrDefn().hashCode() );
         result = 37 * result + ( getYrDefn() == null ? 0 : this.getYrDefn().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}