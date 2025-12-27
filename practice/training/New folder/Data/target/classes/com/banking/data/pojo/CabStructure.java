package com.banking.data.pojo;
// default package



/**
 * CabStructure entity. @author MyEclipse Persistence Tools
 */

public class CabStructure  implements java.io.Serializable {


    // Fields    

     private CabStructureId id;
     private Integer noOfCols;


    // Constructors

    /** default constructor */
    public CabStructure() {
    }

	/** minimal constructor */
    public CabStructure(CabStructureId id) {
        this.id = id;
    }
    
    /** full constructor */
    public CabStructure(CabStructureId id, Integer noOfCols) {
        this.id = id;
        this.noOfCols = noOfCols;
    }

   
    // Property accessors

    public CabStructureId getId() {
        return this.id;
    }
    
    public void setId(CabStructureId id) {
        this.id = id;
    }

    public Integer getNoOfCols() {
        return this.noOfCols;
    }
    
    public void setNoOfCols(Integer noOfCols) {
        this.noOfCols = noOfCols;
    }
   








}