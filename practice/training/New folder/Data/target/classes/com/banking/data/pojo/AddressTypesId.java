package com.banking.data.pojo;
// default package



/**
 * AddressTypesId entity. @author MyEclipse Persistence Tools
 */

public class AddressTypesId  implements java.io.Serializable {


    // Fields    

     private Integer number;
     private String addrType;


    // Constructors

    /** default constructor */
    public AddressTypesId() {
    }

    
    /** full constructor */
    public AddressTypesId(Integer number, String addrType) {
        this.number = number;
        this.addrType = addrType;
    }

   
    // Property accessors

    public Integer getNumber() {
        return this.number;
    }
    
    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAddrType() {
        return this.addrType;
    }
    
    public void setAddrType(String addrType) {
        this.addrType = addrType;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AddressTypesId) ) return false;
		 AddressTypesId castOther = ( AddressTypesId ) other; 
         
		 return ( (this.getNumber()==castOther.getNumber()) || ( this.getNumber()!=null && castOther.getNumber()!=null && this.getNumber().equals(castOther.getNumber()) ) )
 && ( (this.getAddrType()==castOther.getAddrType()) || ( this.getAddrType()!=null && castOther.getAddrType()!=null && this.getAddrType().equals(castOther.getAddrType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getNumber() == null ? 0 : this.getNumber().hashCode() );
         result = 37 * result + ( getAddrType() == null ? 0 : this.getAddrType().hashCode() );
         return result;
   }   





}