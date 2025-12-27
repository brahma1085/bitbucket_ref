package com.banking.data.pojo;
// default package



/**
 * VehicleMasterLogId entity. @author MyEclipse Persistence Tools
 */

public class VehicleMasterLogId  implements java.io.Serializable {


    // Fields    

     private String acType;
     private Integer acNo;
     private String make;
     private Double cost;
     private String addrdealer;
     private String licno;
     private String validity;
     private String type;
     private String permitno;
     private String pvalidity;
     private String vehfor;
     private String area;
     private String addrpark;
     private String other;


    // Constructors

    /** default constructor */
    public VehicleMasterLogId() {
    }

    
    /** full constructor */
    public VehicleMasterLogId(String acType, Integer acNo, String make, Double cost, String addrdealer, String licno, String validity, String type, String permitno, String pvalidity, String vehfor, String area, String addrpark, String other) {
        this.acType = acType;
        this.acNo = acNo;
        this.make = make;
        this.cost = cost;
        this.addrdealer = addrdealer;
        this.licno = licno;
        this.validity = validity;
        this.type = type;
        this.permitno = permitno;
        this.pvalidity = pvalidity;
        this.vehfor = vehfor;
        this.area = area;
        this.addrpark = addrpark;
        this.other = other;
    }

   
    // Property accessors

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public String getMake() {
        return this.make;
    }
    
    public void setMake(String make) {
        this.make = make;
    }

    public Double getCost() {
        return this.cost;
    }
    
    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getAddrdealer() {
        return this.addrdealer;
    }
    
    public void setAddrdealer(String addrdealer) {
        this.addrdealer = addrdealer;
    }

    public String getLicno() {
        return this.licno;
    }
    
    public void setLicno(String licno) {
        this.licno = licno;
    }

    public String getValidity() {
        return this.validity;
    }
    
    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getPermitno() {
        return this.permitno;
    }
    
    public void setPermitno(String permitno) {
        this.permitno = permitno;
    }

    public String getPvalidity() {
        return this.pvalidity;
    }
    
    public void setPvalidity(String pvalidity) {
        this.pvalidity = pvalidity;
    }

    public String getVehfor() {
        return this.vehfor;
    }
    
    public void setVehfor(String vehfor) {
        this.vehfor = vehfor;
    }

    public String getArea() {
        return this.area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }

    public String getAddrpark() {
        return this.addrpark;
    }
    
    public void setAddrpark(String addrpark) {
        this.addrpark = addrpark;
    }

    public String getOther() {
        return this.other;
    }
    
    public void setOther(String other) {
        this.other = other;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof VehicleMasterLogId) ) return false;
		 VehicleMasterLogId castOther = ( VehicleMasterLogId ) other; 
         
		 return ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getMake()==castOther.getMake()) || ( this.getMake()!=null && castOther.getMake()!=null && this.getMake().equals(castOther.getMake()) ) )
 && ( (this.getCost()==castOther.getCost()) || ( this.getCost()!=null && castOther.getCost()!=null && this.getCost().equals(castOther.getCost()) ) )
 && ( (this.getAddrdealer()==castOther.getAddrdealer()) || ( this.getAddrdealer()!=null && castOther.getAddrdealer()!=null && this.getAddrdealer().equals(castOther.getAddrdealer()) ) )
 && ( (this.getLicno()==castOther.getLicno()) || ( this.getLicno()!=null && castOther.getLicno()!=null && this.getLicno().equals(castOther.getLicno()) ) )
 && ( (this.getValidity()==castOther.getValidity()) || ( this.getValidity()!=null && castOther.getValidity()!=null && this.getValidity().equals(castOther.getValidity()) ) )
 && ( (this.getType()==castOther.getType()) || ( this.getType()!=null && castOther.getType()!=null && this.getType().equals(castOther.getType()) ) )
 && ( (this.getPermitno()==castOther.getPermitno()) || ( this.getPermitno()!=null && castOther.getPermitno()!=null && this.getPermitno().equals(castOther.getPermitno()) ) )
 && ( (this.getPvalidity()==castOther.getPvalidity()) || ( this.getPvalidity()!=null && castOther.getPvalidity()!=null && this.getPvalidity().equals(castOther.getPvalidity()) ) )
 && ( (this.getVehfor()==castOther.getVehfor()) || ( this.getVehfor()!=null && castOther.getVehfor()!=null && this.getVehfor().equals(castOther.getVehfor()) ) )
 && ( (this.getArea()==castOther.getArea()) || ( this.getArea()!=null && castOther.getArea()!=null && this.getArea().equals(castOther.getArea()) ) )
 && ( (this.getAddrpark()==castOther.getAddrpark()) || ( this.getAddrpark()!=null && castOther.getAddrpark()!=null && this.getAddrpark().equals(castOther.getAddrpark()) ) )
 && ( (this.getOther()==castOther.getOther()) || ( this.getOther()!=null && castOther.getOther()!=null && this.getOther().equals(castOther.getOther()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getMake() == null ? 0 : this.getMake().hashCode() );
         result = 37 * result + ( getCost() == null ? 0 : this.getCost().hashCode() );
         result = 37 * result + ( getAddrdealer() == null ? 0 : this.getAddrdealer().hashCode() );
         result = 37 * result + ( getLicno() == null ? 0 : this.getLicno().hashCode() );
         result = 37 * result + ( getValidity() == null ? 0 : this.getValidity().hashCode() );
         result = 37 * result + ( getType() == null ? 0 : this.getType().hashCode() );
         result = 37 * result + ( getPermitno() == null ? 0 : this.getPermitno().hashCode() );
         result = 37 * result + ( getPvalidity() == null ? 0 : this.getPvalidity().hashCode() );
         result = 37 * result + ( getVehfor() == null ? 0 : this.getVehfor().hashCode() );
         result = 37 * result + ( getArea() == null ? 0 : this.getArea().hashCode() );
         result = 37 * result + ( getAddrpark() == null ? 0 : this.getAddrpark().hashCode() );
         result = 37 * result + ( getOther() == null ? 0 : this.getOther().hashCode() );
         return result;
   }   





}