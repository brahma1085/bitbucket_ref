package com.banking.data.pojo;
// default package



/**
 * ShareTypeHistoryId entity. @author MyEclipse Persistence Tools
 */

public class ShareTypeHistoryId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer memCat;
     private String catname;
     private Integer minshare;
     private Integer maxshare;
     private Double shareval;
     private String votePow;
     private String shCap;
     private String susShCap;


    // Constructors

    /** default constructor */
    public ShareTypeHistoryId() {
    }

    
    /** full constructor */
    public ShareTypeHistoryId(String acType, Integer memCat, String catname, Integer minshare, Integer maxshare, Double shareval, String votePow, String shCap, String susShCap) {
        this.acType = acType;
        this.memCat = memCat;
        this.catname = catname;
        this.minshare = minshare;
        this.maxshare = maxshare;
        this.shareval = shareval;
        this.votePow = votePow;
        this.shCap = shCap;
        this.susShCap = susShCap;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getMemCat() {
        return this.memCat;
    }
    
    public void setMemCat(Integer memCat) {
        this.memCat = memCat;
    }

    public String getCatname() {
        return this.catname;
    }
    
    public void setCatname(String catname) {
        this.catname = catname;
    }

    public Integer getMinshare() {
        return this.minshare;
    }
    
    public void setMinshare(Integer minshare) {
        this.minshare = minshare;
    }

    public Integer getMaxshare() {
        return this.maxshare;
    }
    
    public void setMaxshare(Integer maxshare) {
        this.maxshare = maxshare;
    }

    public Double getShareval() {
        return this.shareval;
    }
    
    public void setShareval(Double shareval) {
        this.shareval = shareval;
    }

    public String getVotePow() {
        return this.votePow;
    }
    
    public void setVotePow(String votePow) {
        this.votePow = votePow;
    }

    public String getShCap() {
        return this.shCap;
    }
    
    public void setShCap(String shCap) {
        this.shCap = shCap;
    }

    public String getSusShCap() {
        return this.susShCap;
    }
    
    public void setSusShCap(String susShCap) {
        this.susShCap = susShCap;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ShareTypeHistoryId) ) return false;
		 ShareTypeHistoryId castOther = ( ShareTypeHistoryId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getMemCat()==castOther.getMemCat()) || ( this.getMemCat()!=null && castOther.getMemCat()!=null && this.getMemCat().equals(castOther.getMemCat()) ) )
 && ( (this.getCatname()==castOther.getCatname()) || ( this.getCatname()!=null && castOther.getCatname()!=null && this.getCatname().equals(castOther.getCatname()) ) )
 && ( (this.getMinshare()==castOther.getMinshare()) || ( this.getMinshare()!=null && castOther.getMinshare()!=null && this.getMinshare().equals(castOther.getMinshare()) ) )
 && ( (this.getMaxshare()==castOther.getMaxshare()) || ( this.getMaxshare()!=null && castOther.getMaxshare()!=null && this.getMaxshare().equals(castOther.getMaxshare()) ) )
 && ( (this.getShareval()==castOther.getShareval()) || ( this.getShareval()!=null && castOther.getShareval()!=null && this.getShareval().equals(castOther.getShareval()) ) )
 && ( (this.getVotePow()==castOther.getVotePow()) || ( this.getVotePow()!=null && castOther.getVotePow()!=null && this.getVotePow().equals(castOther.getVotePow()) ) )
 && ( (this.getShCap()==castOther.getShCap()) || ( this.getShCap()!=null && castOther.getShCap()!=null && this.getShCap().equals(castOther.getShCap()) ) )
 && ( (this.getSusShCap()==castOther.getSusShCap()) || ( this.getSusShCap()!=null && castOther.getSusShCap()!=null && this.getSusShCap().equals(castOther.getSusShCap()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getMemCat() == null ? 0 : this.getMemCat().hashCode() );
         result = 37 * result + ( getCatname() == null ? 0 : this.getCatname().hashCode() );
         result = 37 * result + ( getMinshare() == null ? 0 : this.getMinshare().hashCode() );
         result = 37 * result + ( getMaxshare() == null ? 0 : this.getMaxshare().hashCode() );
         result = 37 * result + ( getShareval() == null ? 0 : this.getShareval().hashCode() );
         result = 37 * result + ( getVotePow() == null ? 0 : this.getVotePow().hashCode() );
         result = 37 * result + ( getShCap() == null ? 0 : this.getShCap().hashCode() );
         result = 37 * result + ( getSusShCap() == null ? 0 : this.getSusShCap().hashCode() );
         return result;
   }   





}