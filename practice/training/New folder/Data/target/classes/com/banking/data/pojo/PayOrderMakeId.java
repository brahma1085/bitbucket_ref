package com.banking.data.pojo;
// default package



/**
 * PayOrderMakeId entity. @author MyEclipse Persistence Tools
 */

public class PayOrderMakeId  implements java.io.Serializable {


    // Fields    

     private String poType;
     private String custType;
     private Integer poSno;
     private String poDate;
     private String poPurchaserName;
     private String poActy;
     private Integer poAcno;
     private String poFavourName;
     private String poGltype;
     private Integer poGlcd;
     private String poGlname;
     private Double poAmt;
     private String poMade;
     private Double commAmt;
     private String deTml;
     private String deUser;
     private String deDate;
     private String veTml;
     private String veUser;
     private String veDate;


    // Constructors

    /** default constructor */
    public PayOrderMakeId() {
    }

    
    /** full constructor */
    public PayOrderMakeId(String poType, String custType, Integer poSno, String poDate, String poPurchaserName, String poActy, Integer poAcno, String poFavourName, String poGltype, Integer poGlcd, String poGlname, Double poAmt, String poMade, Double commAmt, String deTml, String deUser, String deDate, String veTml, String veUser, String veDate) {
        this.poType = poType;
        this.custType = custType;
        this.poSno = poSno;
        this.poDate = poDate;
        this.poPurchaserName = poPurchaserName;
        this.poActy = poActy;
        this.poAcno = poAcno;
        this.poFavourName = poFavourName;
        this.poGltype = poGltype;
        this.poGlcd = poGlcd;
        this.poGlname = poGlname;
        this.poAmt = poAmt;
        this.poMade = poMade;
        this.commAmt = commAmt;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.veTml = veTml;
        this.veUser = veUser;
        this.veDate = veDate;
    }

   
    // Property accessors

    public String getPoType() {
        return this.poType;
    }
    
    public void setPoType(String poType) {
        this.poType = poType;
    }

    public String getCustType() {
        return this.custType;
    }
    
    public void setCustType(String custType) {
        this.custType = custType;
    }

    public Integer getPoSno() {
        return this.poSno;
    }
    
    public void setPoSno(Integer poSno) {
        this.poSno = poSno;
    }

    public String getPoDate() {
        return this.poDate;
    }
    
    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public String getPoPurchaserName() {
        return this.poPurchaserName;
    }
    
    public void setPoPurchaserName(String poPurchaserName) {
        this.poPurchaserName = poPurchaserName;
    }

    public String getPoActy() {
        return this.poActy;
    }
    
    public void setPoActy(String poActy) {
        this.poActy = poActy;
    }

    public Integer getPoAcno() {
        return this.poAcno;
    }
    
    public void setPoAcno(Integer poAcno) {
        this.poAcno = poAcno;
    }

    public String getPoFavourName() {
        return this.poFavourName;
    }
    
    public void setPoFavourName(String poFavourName) {
        this.poFavourName = poFavourName;
    }

    public String getPoGltype() {
        return this.poGltype;
    }
    
    public void setPoGltype(String poGltype) {
        this.poGltype = poGltype;
    }

    public Integer getPoGlcd() {
        return this.poGlcd;
    }
    
    public void setPoGlcd(Integer poGlcd) {
        this.poGlcd = poGlcd;
    }

    public String getPoGlname() {
        return this.poGlname;
    }
    
    public void setPoGlname(String poGlname) {
        this.poGlname = poGlname;
    }

    public Double getPoAmt() {
        return this.poAmt;
    }
    
    public void setPoAmt(Double poAmt) {
        this.poAmt = poAmt;
    }

    public String getPoMade() {
        return this.poMade;
    }
    
    public void setPoMade(String poMade) {
        this.poMade = poMade;
    }

    public Double getCommAmt() {
        return this.commAmt;
    }
    
    public void setCommAmt(Double commAmt) {
        this.commAmt = commAmt;
    }

    public String getDeTml() {
        return this.deTml;
    }
    
    public void setDeTml(String deTml) {
        this.deTml = deTml;
    }

    public String getDeUser() {
        return this.deUser;
    }
    
    public void setDeUser(String deUser) {
        this.deUser = deUser;
    }

    public String getDeDate() {
        return this.deDate;
    }
    
    public void setDeDate(String deDate) {
        this.deDate = deDate;
    }

    public String getVeTml() {
        return this.veTml;
    }
    
    public void setVeTml(String veTml) {
        this.veTml = veTml;
    }

    public String getVeUser() {
        return this.veUser;
    }
    
    public void setVeUser(String veUser) {
        this.veUser = veUser;
    }

    public String getVeDate() {
        return this.veDate;
    }
    
    public void setVeDate(String veDate) {
        this.veDate = veDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PayOrderMakeId) ) return false;
		 PayOrderMakeId castOther = ( PayOrderMakeId ) other; 
         
		 return ( (this.getPoType()==castOther.getPoType()) || ( this.getPoType()!=null && castOther.getPoType()!=null && this.getPoType().equals(castOther.getPoType()) ) )
 && ( (this.getCustType()==castOther.getCustType()) || ( this.getCustType()!=null && castOther.getCustType()!=null && this.getCustType().equals(castOther.getCustType()) ) )
 && ( (this.getPoSno()==castOther.getPoSno()) || ( this.getPoSno()!=null && castOther.getPoSno()!=null && this.getPoSno().equals(castOther.getPoSno()) ) )
 && ( (this.getPoDate()==castOther.getPoDate()) || ( this.getPoDate()!=null && castOther.getPoDate()!=null && this.getPoDate().equals(castOther.getPoDate()) ) )
 && ( (this.getPoPurchaserName()==castOther.getPoPurchaserName()) || ( this.getPoPurchaserName()!=null && castOther.getPoPurchaserName()!=null && this.getPoPurchaserName().equals(castOther.getPoPurchaserName()) ) )
 && ( (this.getPoActy()==castOther.getPoActy()) || ( this.getPoActy()!=null && castOther.getPoActy()!=null && this.getPoActy().equals(castOther.getPoActy()) ) )
 && ( (this.getPoAcno()==castOther.getPoAcno()) || ( this.getPoAcno()!=null && castOther.getPoAcno()!=null && this.getPoAcno().equals(castOther.getPoAcno()) ) )
 && ( (this.getPoFavourName()==castOther.getPoFavourName()) || ( this.getPoFavourName()!=null && castOther.getPoFavourName()!=null && this.getPoFavourName().equals(castOther.getPoFavourName()) ) )
 && ( (this.getPoGltype()==castOther.getPoGltype()) || ( this.getPoGltype()!=null && castOther.getPoGltype()!=null && this.getPoGltype().equals(castOther.getPoGltype()) ) )
 && ( (this.getPoGlcd()==castOther.getPoGlcd()) || ( this.getPoGlcd()!=null && castOther.getPoGlcd()!=null && this.getPoGlcd().equals(castOther.getPoGlcd()) ) )
 && ( (this.getPoGlname()==castOther.getPoGlname()) || ( this.getPoGlname()!=null && castOther.getPoGlname()!=null && this.getPoGlname().equals(castOther.getPoGlname()) ) )
 && ( (this.getPoAmt()==castOther.getPoAmt()) || ( this.getPoAmt()!=null && castOther.getPoAmt()!=null && this.getPoAmt().equals(castOther.getPoAmt()) ) )
 && ( (this.getPoMade()==castOther.getPoMade()) || ( this.getPoMade()!=null && castOther.getPoMade()!=null && this.getPoMade().equals(castOther.getPoMade()) ) )
 && ( (this.getCommAmt()==castOther.getCommAmt()) || ( this.getCommAmt()!=null && castOther.getCommAmt()!=null && this.getCommAmt().equals(castOther.getCommAmt()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getVeTml()==castOther.getVeTml()) || ( this.getVeTml()!=null && castOther.getVeTml()!=null && this.getVeTml().equals(castOther.getVeTml()) ) )
 && ( (this.getVeUser()==castOther.getVeUser()) || ( this.getVeUser()!=null && castOther.getVeUser()!=null && this.getVeUser().equals(castOther.getVeUser()) ) )
 && ( (this.getVeDate()==castOther.getVeDate()) || ( this.getVeDate()!=null && castOther.getVeDate()!=null && this.getVeDate().equals(castOther.getVeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPoType() == null ? 0 : this.getPoType().hashCode() );
         result = 37 * result + ( getCustType() == null ? 0 : this.getCustType().hashCode() );
         result = 37 * result + ( getPoSno() == null ? 0 : this.getPoSno().hashCode() );
         result = 37 * result + ( getPoDate() == null ? 0 : this.getPoDate().hashCode() );
         result = 37 * result + ( getPoPurchaserName() == null ? 0 : this.getPoPurchaserName().hashCode() );
         result = 37 * result + ( getPoActy() == null ? 0 : this.getPoActy().hashCode() );
         result = 37 * result + ( getPoAcno() == null ? 0 : this.getPoAcno().hashCode() );
         result = 37 * result + ( getPoFavourName() == null ? 0 : this.getPoFavourName().hashCode() );
         result = 37 * result + ( getPoGltype() == null ? 0 : this.getPoGltype().hashCode() );
         result = 37 * result + ( getPoGlcd() == null ? 0 : this.getPoGlcd().hashCode() );
         result = 37 * result + ( getPoGlname() == null ? 0 : this.getPoGlname().hashCode() );
         result = 37 * result + ( getPoAmt() == null ? 0 : this.getPoAmt().hashCode() );
         result = 37 * result + ( getPoMade() == null ? 0 : this.getPoMade().hashCode() );
         result = 37 * result + ( getCommAmt() == null ? 0 : this.getCommAmt().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getVeTml() == null ? 0 : this.getVeTml().hashCode() );
         result = 37 * result + ( getVeUser() == null ? 0 : this.getVeUser().hashCode() );
         result = 37 * result + ( getVeDate() == null ? 0 : this.getVeDate().hashCode() );
         return result;
   }   





}