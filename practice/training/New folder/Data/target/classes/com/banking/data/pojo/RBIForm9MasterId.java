package com.banking.data.pojo;
// default package

import java.sql.Timestamp;
import java.util.Date;


/**
 * RBIForm9MasterId entity. @author MyEclipse Persistence Tools
 */

public class RBIForm9MasterId  implements java.io.Serializable {


    // Fields    

     private Integer code;
     private String name;
     private Date fromdate;
     private Date todate;
     private Integer form9link;
     private String deTml;
     private String deUser;
     private Timestamp deDtTime;


    // Constructors

    /** default constructor */
    public RBIForm9MasterId() {
    }

    
    /** full constructor */
    public RBIForm9MasterId(Integer code, String name, Date fromdate, Date todate, Integer form9link, String deTml, String deUser, Timestamp deDtTime) {
        this.code = code;
        this.name = name;
        this.fromdate = fromdate;
        this.todate = todate;
        this.form9link = form9link;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDtTime = deDtTime;
    }

   
    // Property accessors

    public Integer getCode() {
        return this.code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
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

    public Integer getForm9link() {
        return this.form9link;
    }
    
    public void setForm9link(Integer form9link) {
        this.form9link = form9link;
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

    public Timestamp getDeDtTime() {
        return this.deDtTime;
    }
    
    public void setDeDtTime(Timestamp deDtTime) {
        this.deDtTime = deDtTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RBIForm9MasterId) ) return false;
		 RBIForm9MasterId castOther = ( RBIForm9MasterId ) other; 
         
		 return ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getFromdate()==castOther.getFromdate()) || ( this.getFromdate()!=null && castOther.getFromdate()!=null && this.getFromdate().equals(castOther.getFromdate()) ) )
 && ( (this.getTodate()==castOther.getTodate()) || ( this.getTodate()!=null && castOther.getTodate()!=null && this.getTodate().equals(castOther.getTodate()) ) )
 && ( (this.getForm9link()==castOther.getForm9link()) || ( this.getForm9link()!=null && castOther.getForm9link()!=null && this.getForm9link().equals(castOther.getForm9link()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDtTime()==castOther.getDeDtTime()) || ( this.getDeDtTime()!=null && castOther.getDeDtTime()!=null && this.getDeDtTime().equals(castOther.getDeDtTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getFromdate() == null ? 0 : this.getFromdate().hashCode() );
         result = 37 * result + ( getTodate() == null ? 0 : this.getTodate().hashCode() );
         result = 37 * result + ( getForm9link() == null ? 0 : this.getForm9link().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDtTime() == null ? 0 : this.getDeDtTime().hashCode() );
         return result;
   }   





}