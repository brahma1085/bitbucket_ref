package com.banking.data.pojo;
// default package



/**
 * CabStructureId entity. @author MyEclipse Persistence Tools
 */

public class CabStructureId  implements java.io.Serializable {


    // Fields    

     private Integer cabNo;
     private Integer rowNo;


    // Constructors

    /** default constructor */
    public CabStructureId() {
    }

    
    /** full constructor */
    public CabStructureId(Integer cabNo, Integer rowNo) {
        this.cabNo = cabNo;
        this.rowNo = rowNo;
    }

   
    // Property accessors

    public Integer getCabNo() {
        return this.cabNo;
    }
    
    public void setCabNo(Integer cabNo) {
        this.cabNo = cabNo;
    }

    public Integer getRowNo() {
        return this.rowNo;
    }
    
    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CabStructureId) ) return false;
		 CabStructureId castOther = ( CabStructureId ) other; 
         
		 return ( (this.getCabNo()==castOther.getCabNo()) || ( this.getCabNo()!=null && castOther.getCabNo()!=null && this.getCabNo().equals(castOther.getCabNo()) ) )
 && ( (this.getRowNo()==castOther.getRowNo()) || ( this.getRowNo()!=null && castOther.getRowNo()!=null && this.getRowNo().equals(castOther.getRowNo()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getCabNo() == null ? 0 : this.getCabNo().hashCode() );
         result = 37 * result + ( getRowNo() == null ? 0 : this.getRowNo().hashCode() );
         return result;
   }   





}