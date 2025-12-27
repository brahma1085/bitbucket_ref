package com.banking.data.pojo;
// default package



/**
 * DirectorRelationId entity. @author MyEclipse Persistence Tools
 */

public class DirectorRelationId  implements java.io.Serializable {


    // Fields    

     private Integer relCode;
     private String relationType;


    // Constructors

    /** default constructor */
    public DirectorRelationId() {
    }

    
    /** full constructor */
    public DirectorRelationId(Integer relCode, String relationType) {
        this.relCode = relCode;
        this.relationType = relationType;
    }

   
    // Property accessors

    public Integer getRelCode() {
        return this.relCode;
    }
    
    public void setRelCode(Integer relCode) {
        this.relCode = relCode;
    }

    public String getRelationType() {
        return this.relationType;
    }
    
    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof DirectorRelationId) ) return false;
		 DirectorRelationId castOther = ( DirectorRelationId ) other; 
         
		 return ( (this.getRelCode()==castOther.getRelCode()) || ( this.getRelCode()!=null && castOther.getRelCode()!=null && this.getRelCode().equals(castOther.getRelCode()) ) )
 && ( (this.getRelationType()==castOther.getRelationType()) || ( this.getRelationType()!=null && castOther.getRelationType()!=null && this.getRelationType().equals(castOther.getRelationType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRelCode() == null ? 0 : this.getRelCode().hashCode() );
         result = 37 * result + ( getRelationType() == null ? 0 : this.getRelationType().hashCode() );
         return result;
   }   





}