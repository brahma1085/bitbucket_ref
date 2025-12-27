package com.banking.data.pojo;
// default package



/**
 * CustomerTypeId entity. @author MyEclipse Persistence Tools
 */

public class CustomerTypeId  implements java.io.Serializable {


    // Fields    

     private String custype;
     private Integer custcode;


    // Constructors

    /** default constructor */
    public CustomerTypeId() {
    }

    
    /** full constructor */
    public CustomerTypeId(String custype, Integer custcode) {
        this.custype = custype;
        this.custcode = custcode;
    }

   
    // Property accessors

    public String getCustype() {
        return this.custype;
    }
    
    public void setCustype(String custype) {
        this.custype = custype;
    }

    public Integer getCustcode() {
        return this.custcode;
    }
    
    public void setCustcode(Integer custcode) {
        this.custcode = custcode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CustomerTypeId) ) return false;
		 CustomerTypeId castOther = ( CustomerTypeId ) other; 
         
		 return ( (this.getCustype()==castOther.getCustype()) || ( this.getCustype()!=null && castOther.getCustype()!=null && this.getCustype().equals(castOther.getCustype()) ) )
 && ( (this.getCustcode()==castOther.getCustcode()) || ( this.getCustcode()!=null && castOther.getCustcode()!=null && this.getCustcode().equals(castOther.getCustcode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCustype() == null ? 0 : this.getCustype().hashCode() );
         result = 37 * result + ( getCustcode() == null ? 0 : this.getCustcode().hashCode() );
         return result;
   }   





}