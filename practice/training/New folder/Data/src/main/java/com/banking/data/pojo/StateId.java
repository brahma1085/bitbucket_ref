package com.banking.data.pojo;
// default package



/**
 * StateId entity. @author MyEclipse Persistence Tools
 */

public class StateId  implements java.io.Serializable {


    // Fields    

     private String code;
     private String state;


    // Constructors

    /** default constructor */
    public StateId() {
    }

    
    /** full constructor */
    public StateId(String code, String state) {
        this.code = code;
        this.state = state;
    }

   
    // Property accessors

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof StateId) ) return false;
		 StateId castOther = ( StateId ) other; 
         
		 return ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getState()==castOther.getState()) || ( this.getState()!=null && castOther.getState()!=null && this.getState().equals(castOther.getState()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         result = 37 * result + ( getState() == null ? 0 : this.getState().hashCode() );
         return result;
   }   





}