package com.banking.data.pojo;
// default package



/**
 * AccountSubCategoryId entity. @author MyEclipse Persistence Tools
 */

public class AccountSubCategoryId  implements java.io.Serializable {


    // Fields    

     private Integer subcatcode;
     private String subcatdesc;
     private Integer catcode;
     private Integer age;


    // Constructors

    /** default constructor */
    public AccountSubCategoryId() {
    }

    
    /** full constructor */
    public AccountSubCategoryId(Integer subcatcode, String subcatdesc, Integer catcode, Integer age) {
        this.subcatcode = subcatcode;
        this.subcatdesc = subcatdesc;
        this.catcode = catcode;
        this.age = age;
    }

   
    // Property accessors

    public Integer getSubcatcode() {
        return this.subcatcode;
    }
    
    public void setSubcatcode(Integer subcatcode) {
        this.subcatcode = subcatcode;
    }

    public String getSubcatdesc() {
        return this.subcatdesc;
    }
    
    public void setSubcatdesc(String subcatdesc) {
        this.subcatdesc = subcatdesc;
    }

    public Integer getCatcode() {
        return this.catcode;
    }
    
    public void setCatcode(Integer catcode) {
        this.catcode = catcode;
    }

    public Integer getAge() {
        return this.age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof AccountSubCategoryId) ) return false;
		 AccountSubCategoryId castOther = ( AccountSubCategoryId ) other; 
         
		 return ( (this.getSubcatcode()==castOther.getSubcatcode()) || ( this.getSubcatcode()!=null && castOther.getSubcatcode()!=null && this.getSubcatcode().equals(castOther.getSubcatcode()) ) )
 && ( (this.getSubcatdesc()==castOther.getSubcatdesc()) || ( this.getSubcatdesc()!=null && castOther.getSubcatdesc()!=null && this.getSubcatdesc().equals(castOther.getSubcatdesc()) ) )
 && ( (this.getCatcode()==castOther.getCatcode()) || ( this.getCatcode()!=null && castOther.getCatcode()!=null && this.getCatcode().equals(castOther.getCatcode()) ) )
 && ( (this.getAge()==castOther.getAge()) || ( this.getAge()!=null && castOther.getAge()!=null && this.getAge().equals(castOther.getAge()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getSubcatcode() == null ? 0 : this.getSubcatcode().hashCode() );
         result = 37 * result + ( getSubcatdesc() == null ? 0 : this.getSubcatdesc().hashCode() );
         result = 37 * result + ( getCatcode() == null ? 0 : this.getCatcode().hashCode() );
         result = 37 * result + ( getAge() == null ? 0 : this.getAge().hashCode() );
         return result;
   }   





}