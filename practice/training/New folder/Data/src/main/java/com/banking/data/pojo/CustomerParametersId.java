package com.banking.data.pojo;
// default package



/**
 * CustomerParametersId entity. @author MyEclipse Persistence Tools
 */

public class CustomerParametersId  implements java.io.Serializable {


    // Fields    

     private String tablename;
     private String colname;


    // Constructors

    /** default constructor */
    public CustomerParametersId() {
    }

    
    /** full constructor */
    public CustomerParametersId(String tablename, String colname) {
        this.tablename = tablename;
        this.colname = colname;
    }

   
    // Property accessors

    public String getTablename() {
        return this.tablename;
    }
    
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getColname() {
        return this.colname;
    }
    
    public void setColname(String colname) {
        this.colname = colname;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CustomerParametersId) ) return false;
		 CustomerParametersId castOther = ( CustomerParametersId ) other; 
         
		 return ( (this.getTablename()==castOther.getTablename()) || ( this.getTablename()!=null && castOther.getTablename()!=null && this.getTablename().equals(castOther.getTablename()) ) )
 && ( (this.getColname()==castOther.getColname()) || ( this.getColname()!=null && castOther.getColname()!=null && this.getColname().equals(castOther.getColname()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTablename() == null ? 0 : this.getTablename().hashCode() );
         result = 37 * result + ( getColname() == null ? 0 : this.getColname().hashCode() );
         return result;
   }   





}