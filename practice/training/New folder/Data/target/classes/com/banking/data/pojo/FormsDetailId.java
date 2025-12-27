package com.banking.data.pojo;
// default package



/**
 * FormsDetailId entity. @author MyEclipse Persistence Tools
 */

public class FormsDetailId  implements java.io.Serializable {


    // Fields    

     private String formcode;
     private String formname;
     private String pageId;


    // Constructors

    /** default constructor */
    public FormsDetailId() {
    }

    
    /** full constructor */
    public FormsDetailId(String formcode, String formname, String pageId) {
        this.formcode = formcode;
        this.formname = formname;
        this.pageId = pageId;
    }

   
    // Property accessors

    public String getFormcode() {
        return this.formcode;
    }
    
    public void setFormcode(String formcode) {
        this.formcode = formcode;
    }

    public String getFormname() {
        return this.formname;
    }
    
    public void setFormname(String formname) {
        this.formname = formname;
    }

    public String getPageId() {
        return this.pageId;
    }
    
    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof FormsDetailId) ) return false;
		 FormsDetailId castOther = ( FormsDetailId ) other; 
         
		 return ( (this.getFormcode()==castOther.getFormcode()) || ( this.getFormcode()!=null && castOther.getFormcode()!=null && this.getFormcode().equals(castOther.getFormcode()) ) )
 && ( (this.getFormname()==castOther.getFormname()) || ( this.getFormname()!=null && castOther.getFormname()!=null && this.getFormname().equals(castOther.getFormname()) ) )
 && ( (this.getPageId()==castOther.getPageId()) || ( this.getPageId()!=null && castOther.getPageId()!=null && this.getPageId().equals(castOther.getPageId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFormcode() == null ? 0 : this.getFormcode().hashCode() );
         result = 37 * result + ( getFormname() == null ? 0 : this.getFormname().hashCode() );
         result = 37 * result + ( getPageId() == null ? 0 : this.getPageId().hashCode() );
         return result;
   }   





}