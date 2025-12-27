package com.banking.data.pojo;
// default package



/**
 * CurrencyStockId entity. @author MyEclipse Persistence Tools
 */

public class CurrencyStockId  implements java.io.Serializable {


    // Fields    

     private String tmlNo;
     private String recType;
     private String curDate;
     private Double netamt;
     private Integer s1000;
     private Integer s500;
     private Integer s100;
     private Integer s50;
     private Integer s20;
     private Integer s10;
     private Integer s5;
     private Integer s2;
     private Integer s1;
     private Double scoins;
     private String deTml;
     private String deUser;
     private String deDate;


    // Constructors

    /** default constructor */
    public CurrencyStockId() {
    }

    
    /** full constructor */
    public CurrencyStockId(String tmlNo, String recType, String curDate, Double netamt, Integer s1000, Integer s500, Integer s100, Integer s50, Integer s20, Integer s10, Integer s5, Integer s2, Integer s1, Double scoins, String deTml, String deUser, String deDate) {
        this.tmlNo = tmlNo;
        this.recType = recType;
        this.curDate = curDate;
        this.netamt = netamt;
        this.s1000 = s1000;
        this.s500 = s500;
        this.s100 = s100;
        this.s50 = s50;
        this.s20 = s20;
        this.s10 = s10;
        this.s5 = s5;
        this.s2 = s2;
        this.s1 = s1;
        this.scoins = scoins;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
    }

   
    // Property accessors

    public String getTmlNo() {
        return this.tmlNo;
    }
    
    public void setTmlNo(String tmlNo) {
        this.tmlNo = tmlNo;
    }

    public String getRecType() {
        return this.recType;
    }
    
    public void setRecType(String recType) {
        this.recType = recType;
    }

    public String getCurDate() {
        return this.curDate;
    }
    
    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    public Double getNetamt() {
        return this.netamt;
    }
    
    public void setNetamt(Double netamt) {
        this.netamt = netamt;
    }

    public Integer getS1000() {
        return this.s1000;
    }
    
    public void setS1000(Integer s1000) {
        this.s1000 = s1000;
    }

    public Integer getS500() {
        return this.s500;
    }
    
    public void setS500(Integer s500) {
        this.s500 = s500;
    }

    public Integer getS100() {
        return this.s100;
    }
    
    public void setS100(Integer s100) {
        this.s100 = s100;
    }

    public Integer getS50() {
        return this.s50;
    }
    
    public void setS50(Integer s50) {
        this.s50 = s50;
    }

    public Integer getS20() {
        return this.s20;
    }
    
    public void setS20(Integer s20) {
        this.s20 = s20;
    }

    public Integer getS10() {
        return this.s10;
    }
    
    public void setS10(Integer s10) {
        this.s10 = s10;
    }

    public Integer getS5() {
        return this.s5;
    }
    
    public void setS5(Integer s5) {
        this.s5 = s5;
    }

    public Integer getS2() {
        return this.s2;
    }
    
    public void setS2(Integer s2) {
        this.s2 = s2;
    }

    public Integer getS1() {
        return this.s1;
    }
    
    public void setS1(Integer s1) {
        this.s1 = s1;
    }

    public Double getScoins() {
        return this.scoins;
    }
    
    public void setScoins(Double scoins) {
        this.scoins = scoins;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CurrencyStockId) ) return false;
		 CurrencyStockId castOther = ( CurrencyStockId ) other; 
         
		 return ( (this.getTmlNo()==castOther.getTmlNo()) || ( this.getTmlNo()!=null && castOther.getTmlNo()!=null && this.getTmlNo().equals(castOther.getTmlNo()) ) )
 && ( (this.getRecType()==castOther.getRecType()) || ( this.getRecType()!=null && castOther.getRecType()!=null && this.getRecType().equals(castOther.getRecType()) ) )
 && ( (this.getCurDate()==castOther.getCurDate()) || ( this.getCurDate()!=null && castOther.getCurDate()!=null && this.getCurDate().equals(castOther.getCurDate()) ) )
 && ( (this.getNetamt()==castOther.getNetamt()) || ( this.getNetamt()!=null && castOther.getNetamt()!=null && this.getNetamt().equals(castOther.getNetamt()) ) )
 && ( (this.getS1000()==castOther.getS1000()) || ( this.getS1000()!=null && castOther.getS1000()!=null && this.getS1000().equals(castOther.getS1000()) ) )
 && ( (this.getS500()==castOther.getS500()) || ( this.getS500()!=null && castOther.getS500()!=null && this.getS500().equals(castOther.getS500()) ) )
 && ( (this.getS100()==castOther.getS100()) || ( this.getS100()!=null && castOther.getS100()!=null && this.getS100().equals(castOther.getS100()) ) )
 && ( (this.getS50()==castOther.getS50()) || ( this.getS50()!=null && castOther.getS50()!=null && this.getS50().equals(castOther.getS50()) ) )
 && ( (this.getS20()==castOther.getS20()) || ( this.getS20()!=null && castOther.getS20()!=null && this.getS20().equals(castOther.getS20()) ) )
 && ( (this.getS10()==castOther.getS10()) || ( this.getS10()!=null && castOther.getS10()!=null && this.getS10().equals(castOther.getS10()) ) )
 && ( (this.getS5()==castOther.getS5()) || ( this.getS5()!=null && castOther.getS5()!=null && this.getS5().equals(castOther.getS5()) ) )
 && ( (this.getS2()==castOther.getS2()) || ( this.getS2()!=null && castOther.getS2()!=null && this.getS2().equals(castOther.getS2()) ) )
 && ( (this.getS1()==castOther.getS1()) || ( this.getS1()!=null && castOther.getS1()!=null && this.getS1().equals(castOther.getS1()) ) )
 && ( (this.getScoins()==castOther.getScoins()) || ( this.getScoins()!=null && castOther.getScoins()!=null && this.getScoins().equals(castOther.getScoins()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getTmlNo() == null ? 0 : this.getTmlNo().hashCode() );
         result = 37 * result + ( getRecType() == null ? 0 : this.getRecType().hashCode() );
         result = 37 * result + ( getCurDate() == null ? 0 : this.getCurDate().hashCode() );
         result = 37 * result + ( getNetamt() == null ? 0 : this.getNetamt().hashCode() );
         result = 37 * result + ( getS1000() == null ? 0 : this.getS1000().hashCode() );
         result = 37 * result + ( getS500() == null ? 0 : this.getS500().hashCode() );
         result = 37 * result + ( getS100() == null ? 0 : this.getS100().hashCode() );
         result = 37 * result + ( getS50() == null ? 0 : this.getS50().hashCode() );
         result = 37 * result + ( getS20() == null ? 0 : this.getS20().hashCode() );
         result = 37 * result + ( getS10() == null ? 0 : this.getS10().hashCode() );
         result = 37 * result + ( getS5() == null ? 0 : this.getS5().hashCode() );
         result = 37 * result + ( getS2() == null ? 0 : this.getS2().hashCode() );
         result = 37 * result + ( getS1() == null ? 0 : this.getS1().hashCode() );
         result = 37 * result + ( getScoins() == null ? 0 : this.getScoins().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         return result;
   }   





}