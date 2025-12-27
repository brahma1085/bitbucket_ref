package com.banking.data.pojo;
// default package



/**
 * CountryId entity. @author MyEclipse Persistence Tools
 */

public class CountryId  implements java.io.Serializable {


    // Fields    

     private String country;
     private String code;


    // Constructors

    /** default constructor */
    public CountryId() {
    }

	/** minimal constructor */
    public CountryId(String code) {
        this.code = code;
    }
    
    /** full constructor */
    public CountryId(String country, String code) {
        this.country = country;
        this.code = code;
    }

   
    // Property accessors

    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CountryId) ) return false;
		 CountryId castOther = ( CountryId ) other; 
         
		 return ( (this.getCountry()==castOther.getCountry()) || ( this.getCountry()!=null && castOther.getCountry()!=null && this.getCountry().equals(castOther.getCountry()) ) )
 && ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCountry() == null ? 0 : this.getCountry().hashCode() );
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         return result;
   }   





}