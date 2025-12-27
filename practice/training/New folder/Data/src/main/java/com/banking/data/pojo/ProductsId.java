package com.banking.data.pojo;
// default package



/**
 * ProductsId entity. @author MyEclipse Persistence Tools
 */

public class ProductsId  implements java.io.Serializable {


    // Fields    

     private String dpdlDate;
     private String prodDate;
     private String rinveProdDate;


    // Constructors

    /** default constructor */
    public ProductsId() {
    }

    
    /** full constructor */
    public ProductsId(String dpdlDate, String prodDate, String rinveProdDate) {
        this.dpdlDate = dpdlDate;
        this.prodDate = prodDate;
        this.rinveProdDate = rinveProdDate;
    }

   
    // Property accessors

    public String getDpdlDate() {
        return this.dpdlDate;
    }
    
    public void setDpdlDate(String dpdlDate) {
        this.dpdlDate = dpdlDate;
    }

    public String getProdDate() {
        return this.prodDate;
    }
    
    public void setProdDate(String prodDate) {
        this.prodDate = prodDate;
    }

    public String getRinveProdDate() {
        return this.rinveProdDate;
    }
    
    public void setRinveProdDate(String rinveProdDate) {
        this.rinveProdDate = rinveProdDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ProductsId) ) return false;
		 ProductsId castOther = ( ProductsId ) other; 
         
		 return ( (this.getDpdlDate()==castOther.getDpdlDate()) || ( this.getDpdlDate()!=null && castOther.getDpdlDate()!=null && this.getDpdlDate().equals(castOther.getDpdlDate()) ) )
 && ( (this.getProdDate()==castOther.getProdDate()) || ( this.getProdDate()!=null && castOther.getProdDate()!=null && this.getProdDate().equals(castOther.getProdDate()) ) )
 && ( (this.getRinveProdDate()==castOther.getRinveProdDate()) || ( this.getRinveProdDate()!=null && castOther.getRinveProdDate()!=null && this.getRinveProdDate().equals(castOther.getRinveProdDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDpdlDate() == null ? 0 : this.getDpdlDate().hashCode() );
         result = 37 * result + ( getProdDate() == null ? 0 : this.getProdDate().hashCode() );
         result = 37 * result + ( getRinveProdDate() == null ? 0 : this.getRinveProdDate().hashCode() );
         return result;
   }   





}