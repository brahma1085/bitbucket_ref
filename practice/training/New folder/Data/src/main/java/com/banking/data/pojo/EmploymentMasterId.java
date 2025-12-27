package com.banking.data.pojo;
// default package



/**
 * EmploymentMasterId entity. @author MyEclipse Persistence Tools
 */

public class EmploymentMasterId  implements java.io.Serializable {


    // Fields    

     private Integer acNo;
     private String acType;
     private String empType;
     private String concernName;
     private String natureOfEmp;
     private String employerName;
     private String addr;
     private String phNo;
     private String empNo;
     private String designation;
     private String department;
     private Integer servLength;
     private String empConfirmed;
     private String servTrans;
     private String salCertEnclosed;
     private Double amtMnth;
     private Double taxExpMnth;
     private String bankName;
     private String bankAcType;
     private Integer bankAcNo;
     private Double stockVal;
     private String goodsCond;
     private String goodsType;
     private Double avgTurnoverMnth;
     private Double netMonthIncome;


    // Constructors

    /** default constructor */
    public EmploymentMasterId() {
    }

    
    /** full constructor */
    public EmploymentMasterId(Integer acNo, String acType, String empType, String concernName, String natureOfEmp, String employerName, String addr, String phNo, String empNo, String designation, String department, Integer servLength, String empConfirmed, String servTrans, String salCertEnclosed, Double amtMnth, Double taxExpMnth, String bankName, String bankAcType, Integer bankAcNo, Double stockVal, String goodsCond, String goodsType, Double avgTurnoverMnth, Double netMonthIncome) {
        this.acNo = acNo;
        this.acType = acType;
        this.empType = empType;
        this.concernName = concernName;
        this.natureOfEmp = natureOfEmp;
        this.employerName = employerName;
        this.addr = addr;
        this.phNo = phNo;
        this.empNo = empNo;
        this.designation = designation;
        this.department = department;
        this.servLength = servLength;
        this.empConfirmed = empConfirmed;
        this.servTrans = servTrans;
        this.salCertEnclosed = salCertEnclosed;
        this.amtMnth = amtMnth;
        this.taxExpMnth = taxExpMnth;
        this.bankName = bankName;
        this.bankAcType = bankAcType;
        this.bankAcNo = bankAcNo;
        this.stockVal = stockVal;
        this.goodsCond = goodsCond;
        this.goodsType = goodsType;
        this.avgTurnoverMnth = avgTurnoverMnth;
        this.netMonthIncome = netMonthIncome;
    }

   
    // Property accessors

    public Integer getAcNo() {
        return this.acNo;
    }
    
    public void setAcNo(Integer acNo) {
        this.acNo = acNo;
    }

    public String getAcType() {
        return this.acType;
    }
    
    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getEmpType() {
        return this.empType;
    }
    
    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public String getConcernName() {
        return this.concernName;
    }
    
    public void setConcernName(String concernName) {
        this.concernName = concernName;
    }

    public String getNatureOfEmp() {
        return this.natureOfEmp;
    }
    
    public void setNatureOfEmp(String natureOfEmp) {
        this.natureOfEmp = natureOfEmp;
    }

    public String getEmployerName() {
        return this.employerName;
    }
    
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getAddr() {
        return this.addr;
    }
    
    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhNo() {
        return this.phNo;
    }
    
    public void setPhNo(String phNo) {
        this.phNo = phNo;
    }

    public String getEmpNo() {
        return this.empNo;
    }
    
    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getDesignation() {
        return this.designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getServLength() {
        return this.servLength;
    }
    
    public void setServLength(Integer servLength) {
        this.servLength = servLength;
    }

    public String getEmpConfirmed() {
        return this.empConfirmed;
    }
    
    public void setEmpConfirmed(String empConfirmed) {
        this.empConfirmed = empConfirmed;
    }

    public String getServTrans() {
        return this.servTrans;
    }
    
    public void setServTrans(String servTrans) {
        this.servTrans = servTrans;
    }

    public String getSalCertEnclosed() {
        return this.salCertEnclosed;
    }
    
    public void setSalCertEnclosed(String salCertEnclosed) {
        this.salCertEnclosed = salCertEnclosed;
    }

    public Double getAmtMnth() {
        return this.amtMnth;
    }
    
    public void setAmtMnth(Double amtMnth) {
        this.amtMnth = amtMnth;
    }

    public Double getTaxExpMnth() {
        return this.taxExpMnth;
    }
    
    public void setTaxExpMnth(Double taxExpMnth) {
        this.taxExpMnth = taxExpMnth;
    }

    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAcType() {
        return this.bankAcType;
    }
    
    public void setBankAcType(String bankAcType) {
        this.bankAcType = bankAcType;
    }

    public Integer getBankAcNo() {
        return this.bankAcNo;
    }
    
    public void setBankAcNo(Integer bankAcNo) {
        this.bankAcNo = bankAcNo;
    }

    public Double getStockVal() {
        return this.stockVal;
    }
    
    public void setStockVal(Double stockVal) {
        this.stockVal = stockVal;
    }

    public String getGoodsCond() {
        return this.goodsCond;
    }
    
    public void setGoodsCond(String goodsCond) {
        this.goodsCond = goodsCond;
    }

    public String getGoodsType() {
        return this.goodsType;
    }
    
    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Double getAvgTurnoverMnth() {
        return this.avgTurnoverMnth;
    }
    
    public void setAvgTurnoverMnth(Double avgTurnoverMnth) {
        this.avgTurnoverMnth = avgTurnoverMnth;
    }

    public Double getNetMonthIncome() {
        return this.netMonthIncome;
    }
    
    public void setNetMonthIncome(Double netMonthIncome) {
        this.netMonthIncome = netMonthIncome;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof EmploymentMasterId) ) return false;
		 EmploymentMasterId castOther = ( EmploymentMasterId ) other; 
         
		 return ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getEmpType()==castOther.getEmpType()) || ( this.getEmpType()!=null && castOther.getEmpType()!=null && this.getEmpType().equals(castOther.getEmpType()) ) )
 && ( (this.getConcernName()==castOther.getConcernName()) || ( this.getConcernName()!=null && castOther.getConcernName()!=null && this.getConcernName().equals(castOther.getConcernName()) ) )
 && ( (this.getNatureOfEmp()==castOther.getNatureOfEmp()) || ( this.getNatureOfEmp()!=null && castOther.getNatureOfEmp()!=null && this.getNatureOfEmp().equals(castOther.getNatureOfEmp()) ) )
 && ( (this.getEmployerName()==castOther.getEmployerName()) || ( this.getEmployerName()!=null && castOther.getEmployerName()!=null && this.getEmployerName().equals(castOther.getEmployerName()) ) )
 && ( (this.getAddr()==castOther.getAddr()) || ( this.getAddr()!=null && castOther.getAddr()!=null && this.getAddr().equals(castOther.getAddr()) ) )
 && ( (this.getPhNo()==castOther.getPhNo()) || ( this.getPhNo()!=null && castOther.getPhNo()!=null && this.getPhNo().equals(castOther.getPhNo()) ) )
 && ( (this.getEmpNo()==castOther.getEmpNo()) || ( this.getEmpNo()!=null && castOther.getEmpNo()!=null && this.getEmpNo().equals(castOther.getEmpNo()) ) )
 && ( (this.getDesignation()==castOther.getDesignation()) || ( this.getDesignation()!=null && castOther.getDesignation()!=null && this.getDesignation().equals(castOther.getDesignation()) ) )
 && ( (this.getDepartment()==castOther.getDepartment()) || ( this.getDepartment()!=null && castOther.getDepartment()!=null && this.getDepartment().equals(castOther.getDepartment()) ) )
 && ( (this.getServLength()==castOther.getServLength()) || ( this.getServLength()!=null && castOther.getServLength()!=null && this.getServLength().equals(castOther.getServLength()) ) )
 && ( (this.getEmpConfirmed()==castOther.getEmpConfirmed()) || ( this.getEmpConfirmed()!=null && castOther.getEmpConfirmed()!=null && this.getEmpConfirmed().equals(castOther.getEmpConfirmed()) ) )
 && ( (this.getServTrans()==castOther.getServTrans()) || ( this.getServTrans()!=null && castOther.getServTrans()!=null && this.getServTrans().equals(castOther.getServTrans()) ) )
 && ( (this.getSalCertEnclosed()==castOther.getSalCertEnclosed()) || ( this.getSalCertEnclosed()!=null && castOther.getSalCertEnclosed()!=null && this.getSalCertEnclosed().equals(castOther.getSalCertEnclosed()) ) )
 && ( (this.getAmtMnth()==castOther.getAmtMnth()) || ( this.getAmtMnth()!=null && castOther.getAmtMnth()!=null && this.getAmtMnth().equals(castOther.getAmtMnth()) ) )
 && ( (this.getTaxExpMnth()==castOther.getTaxExpMnth()) || ( this.getTaxExpMnth()!=null && castOther.getTaxExpMnth()!=null && this.getTaxExpMnth().equals(castOther.getTaxExpMnth()) ) )
 && ( (this.getBankName()==castOther.getBankName()) || ( this.getBankName()!=null && castOther.getBankName()!=null && this.getBankName().equals(castOther.getBankName()) ) )
 && ( (this.getBankAcType()==castOther.getBankAcType()) || ( this.getBankAcType()!=null && castOther.getBankAcType()!=null && this.getBankAcType().equals(castOther.getBankAcType()) ) )
 && ( (this.getBankAcNo()==castOther.getBankAcNo()) || ( this.getBankAcNo()!=null && castOther.getBankAcNo()!=null && this.getBankAcNo().equals(castOther.getBankAcNo()) ) )
 && ( (this.getStockVal()==castOther.getStockVal()) || ( this.getStockVal()!=null && castOther.getStockVal()!=null && this.getStockVal().equals(castOther.getStockVal()) ) )
 && ( (this.getGoodsCond()==castOther.getGoodsCond()) || ( this.getGoodsCond()!=null && castOther.getGoodsCond()!=null && this.getGoodsCond().equals(castOther.getGoodsCond()) ) )
 && ( (this.getGoodsType()==castOther.getGoodsType()) || ( this.getGoodsType()!=null && castOther.getGoodsType()!=null && this.getGoodsType().equals(castOther.getGoodsType()) ) )
 && ( (this.getAvgTurnoverMnth()==castOther.getAvgTurnoverMnth()) || ( this.getAvgTurnoverMnth()!=null && castOther.getAvgTurnoverMnth()!=null && this.getAvgTurnoverMnth().equals(castOther.getAvgTurnoverMnth()) ) )
 && ( (this.getNetMonthIncome()==castOther.getNetMonthIncome()) || ( this.getNetMonthIncome()!=null && castOther.getNetMonthIncome()!=null && this.getNetMonthIncome().equals(castOther.getNetMonthIncome()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getEmpType() == null ? 0 : this.getEmpType().hashCode() );
         result = 37 * result + ( getConcernName() == null ? 0 : this.getConcernName().hashCode() );
         result = 37 * result + ( getNatureOfEmp() == null ? 0 : this.getNatureOfEmp().hashCode() );
         result = 37 * result + ( getEmployerName() == null ? 0 : this.getEmployerName().hashCode() );
         result = 37 * result + ( getAddr() == null ? 0 : this.getAddr().hashCode() );
         result = 37 * result + ( getPhNo() == null ? 0 : this.getPhNo().hashCode() );
         result = 37 * result + ( getEmpNo() == null ? 0 : this.getEmpNo().hashCode() );
         result = 37 * result + ( getDesignation() == null ? 0 : this.getDesignation().hashCode() );
         result = 37 * result + ( getDepartment() == null ? 0 : this.getDepartment().hashCode() );
         result = 37 * result + ( getServLength() == null ? 0 : this.getServLength().hashCode() );
         result = 37 * result + ( getEmpConfirmed() == null ? 0 : this.getEmpConfirmed().hashCode() );
         result = 37 * result + ( getServTrans() == null ? 0 : this.getServTrans().hashCode() );
         result = 37 * result + ( getSalCertEnclosed() == null ? 0 : this.getSalCertEnclosed().hashCode() );
         result = 37 * result + ( getAmtMnth() == null ? 0 : this.getAmtMnth().hashCode() );
         result = 37 * result + ( getTaxExpMnth() == null ? 0 : this.getTaxExpMnth().hashCode() );
         result = 37 * result + ( getBankName() == null ? 0 : this.getBankName().hashCode() );
         result = 37 * result + ( getBankAcType() == null ? 0 : this.getBankAcType().hashCode() );
         result = 37 * result + ( getBankAcNo() == null ? 0 : this.getBankAcNo().hashCode() );
         result = 37 * result + ( getStockVal() == null ? 0 : this.getStockVal().hashCode() );
         result = 37 * result + ( getGoodsCond() == null ? 0 : this.getGoodsCond().hashCode() );
         result = 37 * result + ( getGoodsType() == null ? 0 : this.getGoodsType().hashCode() );
         result = 37 * result + ( getAvgTurnoverMnth() == null ? 0 : this.getAvgTurnoverMnth().hashCode() );
         result = 37 * result + ( getNetMonthIncome() == null ? 0 : this.getNetMonthIncome().hashCode() );
         return result;
   }   





}