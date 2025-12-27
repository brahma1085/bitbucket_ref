package com.banking.data.pojo;
// default package



/**
 * ProjectPhotos entity. @author MyEclipse Persistence Tools
 */

public class ProjectPhotos  implements java.io.Serializable {


    // Fields    

     private ProjectPhotosId id;


    // Constructors

    /** default constructor */
    public ProjectPhotos() {
    }

    
    /** full constructor */
    public ProjectPhotos(ProjectPhotosId id) {
        this.id = id;
    }

   
    // Property accessors

    public ProjectPhotosId getId() {
        return this.id;
    }
    
    public void setId(ProjectPhotosId id) {
        this.id = id;
    }
   








}