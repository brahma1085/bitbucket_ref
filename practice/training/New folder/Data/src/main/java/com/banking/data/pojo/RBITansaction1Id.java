package com.banking.data.pojo;
// default package



/**
 * RBITansaction1Id entity. @author MyEclipse Persistence Tools
 */

public class RBITansaction1Id  implements java.io.Serializable {


    // Fields    

     private Integer code;
     private String trnSrc;
     private String glType;
     private Integer glCode;
     private Double balance;
     private Integer sequence;


    // Constructors

    /** default constructor */
    public RBITansaction1Id() {
    }

    
    /** full constructor */
    public RBITansaction1Id(Integer code, String trnSrc, String glType, Integer glCode, Double balance, Integer sequence) {
        this.code = code;
        this.trnSrc = trnSrc;
        this.glType = glType;
        this.glCode = glCode;
        this.balance = balance;
        this.sequence = sequence;
    }

   
    // Property accessors

    public Integer getCode() {
        return this.code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTrnSrc() {
        return this.trnSrc;
    }
    
    public void setTrnSrc(String trnSrc) {
        this.trnSrc = trnSrc;
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

    public Double getBalance() {
        return this.balance;
    }
    
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getSequence() {
        return this.sequence;
    }
    
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RBITansaction1Id) ) return false;
		 RBITansaction1Id castOther = ( RBITansaction1Id ) other; 
         
		 return ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getTrnSrc()==castOther.getTrnSrc()) || ( this.getTrnSrc()!=null && castOther.getTrnSrc()!=null && this.getTrnSrc().equals(castOther.getTrnSrc()) ) )
 && ( (this.getGlType()==castOther.getGlType()) || ( this.getGlType()!=null && castOther.getGlType()!=null && this.getGlType().equals(castOther.getGlType()) ) )
 && ( (this.getGlCode()==castOther.getGlCode()) || ( this.getGlCode()!=null && castOther.getGlCode()!=null && this.getGlCode().equals(castOther.getGlCode()) ) )
 && ( (this.getBalance()==castOther.getBalance()) || ( this.getBalance()!=null && castOther.getBalance()!=null && this.getBalance().equals(castOther.getBalance()) ) )
 && ( (this.getSequence()==castOther.getSequence()) || ( this.getSequence()!=null && castOther.getSequence()!=null && this.getSequence().equals(castOther.getSequence()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         result = 37 * result + ( getTrnSrc() == null ? 0 : this.getTrnSrc().hashCode() );
         result = 37 * result + ( getGlType() == null ? 0 : this.getGlType().hashCode() );
         result = 37 * result + ( getGlCode() == null ? 0 : this.getGlCode().hashCode() );
         result = 37 * result + ( getBalance() == null ? 0 : this.getBalance().hashCode() );
         result = 37 * result + ( getSequence() == null ? 0 : this.getSequence().hashCode() );
         return result;
   }   





}