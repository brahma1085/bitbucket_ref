package com.banking.data.pojo;
// default package



/**
 * ProjectPhotosId entity. @author MyEclipse Persistence Tools
 */

public class ProjectPhotosId  implements java.io.Serializable {


    // Fields    

     private String photoName;
     private String photo;


    // Constructors

    /** default constructor */
    public ProjectPhotosId() {
    }

    
    /** full constructor */
    public ProjectPhotosId(String photoName, String photo) {
        this.photoName = photoName;
        this.photo = photo;
    }

   
    // Property accessors

    public String getPhotoName() {
        return this.photoName;
    }
    
    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhoto() {
        return this.photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ProjectPhotosId) ) return false;
		 ProjectPhotosId castOther = ( ProjectPhotosId ) other; 
         
		 return ( (this.getPhotoName()==castOther.getPhotoName()) || ( this.getPhotoName()!=null && castOther.getPhotoName()!=null && this.getPhotoName().equals(castOther.getPhotoName()) ) )
 && ( (this.getPhoto()==castOther.getPhoto()) || ( this.getPhoto()!=null && castOther.getPhoto()!=null && this.getPhoto().equals(castOther.getPhoto()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPhotoName() == null ? 0 : this.getPhotoName().hashCode() );
         result = 37 * result + ( getPhoto() == null ? 0 : this.getPhoto().hashCode() );
         return result;
   }   





}