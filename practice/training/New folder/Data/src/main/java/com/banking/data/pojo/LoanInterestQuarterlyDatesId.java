package com.banking.data.pojo;
// default package



/**
 * LoanInterestQuarterlyDatesId entity. @author MyEclipse Persistence Tools
 */

public class LoanInterestQuarterlyDatesId  implements java.io.Serializable {


    // Fields    

     private String quaterlyDate;
     private Boolean flag;


    // Constructors

    /** default constructor */
    public LoanInterestQuarterlyDatesId() {
    }

    
    /** full constructor */
    public LoanInterestQuarterlyDatesId(String quaterlyDate, Boolean flag) {
        this.quaterlyDate = quaterlyDate;
        this.flag = flag;
    }

   
    // Property accessors

    public String getQuaterlyDate() {
        return this.quaterlyDate;
    }
    
    public void setQuaterlyDate(String quaterlyDate) {
        this.quaterlyDate = quaterlyDate;
    }

    public Boolean getFlag() {
        return this.flag;
    }
    
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof LoanInterestQuarterlyDatesId) ) return false;
		 LoanInterestQuarterlyDatesId castOther = ( LoanInterestQuarterlyDatesId ) other; 
         
		 return ( (this.getQuaterlyDate()==castOther.getQuaterlyDate()) || ( this.getQuaterlyDate()!=null && castOther.getQuaterlyDate()!=null && this.getQuaterlyDate().equals(castOther.getQuaterlyDate()) ) )
 && ( (this.getFlag()==castOther.getFlag()) || ( this.getFlag()!=null && castOther.getFlag()!=null && this.getFlag().equals(castOther.getFlag()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getQuaterlyDate() == null ? 0 : this.getQuaterlyDate().hashCode() );
         result = 37 * result + ( getFlag() == null ? 0 : this.getFlag().hashCode() );
         return result;
   }   





}