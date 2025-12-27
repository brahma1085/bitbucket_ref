package com.banking.data.pojo;
// default package



/**
 * DailyDataId entity. @author MyEclipse Persistence Tools
 */

public class DailyDataId  implements java.io.Serializable {


    // Fields    

     private Integer code;
     private String glType;
     private Integer glCode;
     private String normalCd;
     private Double dayBalance;


    // Constructors

    /** default constructor */
    public DailyDataId() {
    }

	/** minimal constructor */
    public DailyDataId(Double dayBalance) {
        this.dayBalance = dayBalance;
    }
    
    /** full constructor */
    public DailyDataId(Integer code, String glType, Integer glCode, String normalCd, Double dayBalance) {
        this.code = code;
        this.glType = glType;
        this.glCode = glCode;
        this.normalCd = normalCd;
        this.dayBalance = dayBalance;
    }

   
    // Property accessors

    public Integer getCode() {
        return this.code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getGlType() {
        return this.glType;
    }
    
    public void setGlType(String glType) {
        this.glType = glType;
    }

    public Integer getGlCode() {
        return this.glCode;
    }
    
    public void setGlCode(Integer glCode) {
        this.glCode = glCode;
    }

    public String getNormalCd() {
        return this.normalCd;
    }
    
    public void setNormalCd(String normalCd) {
        this.normalCd = normalCd;
    }

    public Double getDayBalance() {
        return this.dayBalance;
    }
    
    public void setDayBalance(Double dayBalance) {
        this.dayBalance = dayBalance;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DailyDataId) ) return false;
		 DailyDataId castOther = ( DailyDataId ) other; 
         
		 return ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getNormalCd()==castOther.getNormalCd()) || ( this.getNormalCd()!=null && castOther.getNormalCd()!=null && this.getNormalCd().equals(castOther.getNormalCd()) ) )
 && ( (this.getDayBalance()==castOther.getDayBalance()) || ( this.getDayBalance()!=null && castOther.getDayBalance()!=null && this.getDayBalance().equals(castOther.getDayBalance()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         result = 37 * result + ( getGlType() == null ? 0 : this.getGlType().hashCode() );
         result = 37 * result + ( getGlCode() == null ? 0 : this.getGlCode().hashCode() );
         result = 37 * result + ( getNormalCd() == null ? 0 : this.getNormalCd().hashCode() );
         result = 37 * result + ( getDayBalance() == null ? 0 : this.getDayBalance().hashCode() );
         return result;
   }   





}