package com.banking.data.pojo;
// default package

import java.util.Date;


/**
 * SecurityDetailsId entity. @author MyEclipse Persistence Tools
 */

public class SecurityDetailsId  implements java.io.Serializable {


    // Fields    

     private Integer code;
     private String typeOfSecurity;
     private String modulecode;
     private Double percLnAvailed;
     private Date fromdate;
     private Date todate;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public SecurityDetailsId() {
    }

    
    /** full constructor */
    public SecurityDetailsId(Integer code, String typeOfSecurity, String modulecode, Double percLnAvailed, Date fromdate, Date todate, String deTml, String deUser, String deDate) {
        this.code = code;
        this.typeOfSecurity = typeOfSecurity;
        this.modulecode = modulecode;
        this.percLnAvailed = percLnAvailed;
        this.fromdate = fromdate;
        this.todate = todate;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public Integer getCode() {
        return this.code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTypeOfSecurity() {
        return this.typeOfSecurity;
    }
    
    public void setTypeOfSecurity(String typeOfSecurity) {
        this.typeOfSecurity = typeOfSecurity;
    }

    public String getModulecode() {
        return this.modulecode;
    }
    
    public void setModulecode(String modulecode) {
        this.modulecode = modulecode;
    }

    public Double getPercLnAvailed() {
        return this.percLnAvailed;
    }
    
    public void setPercLnAvailed(Double percLnAvailed) {
        this.percLnAvailed = percLnAvailed;
    }

    public Date getFromdate() {
        return this.fromdate;
    }
    
    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return this.todate;
    }
    
    public void setTodate(Date todate) {
        this.todate = todate;
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
		 if ( !(other instanceof SecurityDetailsId) ) return false;
		 SecurityDetailsId castOther = ( SecurityDetailsId ) other; 
         
		 return ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getTypeOfSecurity()==castOther.getTypeOfSecurity()) || ( this.getTypeOfSecurity()!=null && castOther.getTypeOfSecurity()!=null && this.getTypeOfSecurity().equals(castOther.getTypeOfSecurity()) ) )
 && ( (this.getModulecode()==castOther.getModulecode()) || ( this.getModulecode()!=null && castOther.getModulecode()!=null && this.getModulecode().equals(castOther.getModulecode()) ) )
 && ( (this.getPercLnAvailed()==castOther.getPercLnAvailed()) || ( this.getPercLnAvailed()!=null && castOther.getPercLnAvailed()!=null && this.getPercLnAvailed().equals(castOther.getPercLnAvailed()) ) )
 && ( (this.getFromdate()==castOther.getFromdate()) || ( this.getFromdate()!=null && castOther.getFromdate()!=null && this.getFromdate().equals(castOther.getFromdate()) ) )
 && ( (this.getTodate()==castOther.getTodate()) || ( this.getTodate()!=null && castOther.getTodate()!=null && this.getTodate().equals(castOther.getTodate()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         result = 37 * result + ( getTypeOfSecurity() == null ? 0 : this.getTypeOfSecurity().hashCode() );
         result = 37 * result + ( getModulecode() == null ? 0 : this.getModulecode().hashCode() );
         result = 37 * result + ( getPercLnAvailed() == null ? 0 : this.getPercLnAvailed().hashCode() );
         result = 37 * result + ( getFromdate() == null ? 0 : this.getFromdate().hashCode() );
         result = 37 * result + ( getTodate() == null ? 0 : this.getTodate().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}