package com.banking.data.pojo;
// default package



/**
 * PSADDMstId entity. @author MyEclipse Persistence Tools
 */

public class PSADDMstId  implements java.io.Serializable {


    // Fields    

     private Integer addrKey;
     private String addr1;
     private String addr2;
     private String addr3;
     private String addr4;
     private String addr5;
     private String deTml;
     private String deUser;
     private String deDate;
     private String deTime;


    // Constructors

    /** default constructor */
    public PSADDMstId() {
    }

    
    /** full constructor */
    public PSADDMstId(Integer addrKey, String addr1, String addr2, String addr3, String addr4, String addr5, String deTml, String deUser, String deDate, String deTime) {
        this.addrKey = addrKey;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.addr3 = addr3;
        this.addr4 = addr4;
        this.addr5 = addr5;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.deTime = deTime;
    }

   
    // Property accessors

    public Integer getAddrKey() {
        return this.addrKey;
    }
    
    public void setAddrKey(Integer addrKey) {
        this.addrKey = addrKey;
    }

    public String getAddr1() {
        return this.addr1;
    }
    
    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return this.addr2;
    }
    
    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr3() {
        return this.addr3;
    }
    
    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getAddr4() {
        return this.addr4;
    }
    
    public void setAddr4(String addr4) {
        this.addr4 = addr4;
    }

    public String getAddr5() {
        return this.addr5;
    }
    
    public void setAddr5(String addr5) {
        this.addr5 = addr5;
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

    public String getDeTime() {
        return this.deTime;
    }
    
    public void setDeTime(String deTime) {
        this.deTime = deTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PSADDMstId) ) return false;
		 PSADDMstId castOther = ( PSADDMstId ) other; 
         
		 return ( (this.getAddrKey()==castOther.getAddrKey()) || ( this.getAddrKey()!=null && castOther.getAddrKey()!=null && this.getAddrKey().equals(castOther.getAddrKey()) ) )
 && ( (this.getAddr1()==castOther.getAddr1()) || ( this.getAddr1()!=null && castOther.getAddr1()!=null && this.getAddr1().equals(castOther.getAddr1()) ) )
 && ( (this.getAddr2()==castOther.getAddr2()) || ( this.getAddr2()!=null && castOther.getAddr2()!=null && this.getAddr2().equals(castOther.getAddr2()) ) )
 && ( (this.getAddr3()==castOther.getAddr3()) || ( this.getAddr3()!=null && castOther.getAddr3()!=null && this.getAddr3().equals(castOther.getAddr3()) ) )
 && ( (this.getAddr4()==castOther.getAddr4()) || ( this.getAddr4()!=null && castOther.getAddr4()!=null && this.getAddr4().equals(castOther.getAddr4()) ) )
 && ( (this.getAddr5()==castOther.getAddr5()) || ( this.getAddr5()!=null && castOther.getAddr5()!=null && this.getAddr5().equals(castOther.getAddr5()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getDeTime()==castOther.getDeTime()) || ( this.getDeTime()!=null && castOther.getDeTime()!=null && this.getDeTime().equals(castOther.getDeTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAddrKey() == null ? 0 : this.getAddrKey().hashCode() );
         result = 37 * result + ( getAddr1() == null ? 0 : this.getAddr1().hashCode() );
         result = 37 * result + ( getAddr2() == null ? 0 : this.getAddr2().hashCode() );
         result = 37 * result + ( getAddr3() == null ? 0 : this.getAddr3().hashCode() );
         result = 37 * result + ( getAddr4() == null ? 0 : this.getAddr4().hashCode() );
         result = 37 * result + ( getAddr5() == null ? 0 : this.getAddr5().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getDeTime() == null ? 0 : this.getDeTime().hashCode() );
         return result;
   }   





}