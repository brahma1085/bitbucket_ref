package com.banking.data.pojo;
// default package



/**
 * AppriserMasterId entity. @author MyEclipse Persistence Tools
 */

public class AppriserMasterId  implements java.io.Serializable {


    // Fields    

     private Integer code;
     private String name;


    // Constructors

    /** default constructor */
    public AppriserMasterId() {
    }

	/** minimal constructor */
    public AppriserMasterId(Integer code) {
        this.code = code;
    }
    
    /** full constructor */
    public AppriserMasterId(Integer code, String name) {
        this.code = code;
        this.name = name;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AppriserMasterId) ) return false;
		 AppriserMasterId castOther = ( AppriserMasterId ) other; 
         
		 return ( (this.getCode()==castOther.getCode()) || ( this.getCode()!=null && castOther.getCode()!=null && this.getCode().equals(castOther.getCode()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCode() == null ? 0 : this.getCode().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         return result;
   }   





}