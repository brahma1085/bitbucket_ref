package com.banking.data.pojo;
// default package



/**
 * CityMasterId entity. @author MyEclipse Persistence Tools
 */

public class CityMasterId  implements java.io.Serializable {


    // Fields    

     private String cityCode;
     private String cityName;


    // Constructors

    /** default constructor */
    public CityMasterId() {
    }

    
    /** full constructor */
    public CityMasterId(String cityCode, String cityName) {
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

   
    // Property accessors

    public String getCityCode() {
        return this.cityCode;
    }
    
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return this.cityName;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CityMasterId) ) return false;
		 CityMasterId castOther = ( CityMasterId ) other; 
         
		 return ( (this.getCityCode()==castOther.getCityCode()) || ( this.getCityCode()!=null && castOther.getCityCode()!=null && this.getCityCode().equals(castOther.getCityCode()) ) )
 && ( (this.getCityName()==castOther.getCityName()) || ( this.getCityName()!=null && castOther.getCityName()!=null && this.getCityName().equals(castOther.getCityName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCityCode() == null ? 0 : this.getCityCode().hashCode() );
         result = 37 * result + ( getCityName() == null ? 0 : this.getCityName().hashCode() );
         return result;
   }   





}