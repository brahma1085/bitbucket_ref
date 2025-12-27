package com.banking.data.pojo;
// default package

import java.util.Date;


/**
 * RemainderNoticesId entity. @author MyEclipse Persistence Tools
 */

public class RemainderNoticesId  implements java.io.Serializable {


    // Fields    

     private Integer noticeNo;
     private Date date;
     private String noticeType;
     private String acType;
     private Integer acNo;


    // Constructors

    /** default constructor */
    public RemainderNoticesId() {
    }

    
    /** full constructor */
    public RemainderNoticesId(Integer noticeNo, Date date, String noticeType, String acType, Integer acNo) {
        this.noticeNo = noticeNo;
        this.date = date;
        this.noticeType = noticeType;
        this.acType = acType;
        this.acNo = acNo;
    }

   
    // Property accessors

    public Integer getNoticeNo() {
        return this.noticeNo;
    }
    
    public void setNoticeNo(Integer noticeNo) {
        this.noticeNo = noticeNo;
    }

    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    public String getNoticeType() {
        return this.noticeType;
    }
    
    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RemainderNoticesId) ) return false;
		 RemainderNoticesId castOther = ( RemainderNoticesId ) other; 
         
		 return ( (this.getNoticeNo()==castOther.getNoticeNo()) || ( this.getNoticeNo()!=null && castOther.getNoticeNo()!=null && this.getNoticeNo().equals(castOther.getNoticeNo()) ) )
 && ( (this.getDate()==castOther.getDate()) || ( this.getDate()!=null && castOther.getDate()!=null && this.getDate().equals(castOther.getDate()) ) )
 && ( (this.getNoticeType()==castOther.getNoticeType()) || ( this.getNoticeType()!=null && castOther.getNoticeType()!=null && this.getNoticeType().equals(castOther.getNoticeType()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getNoticeNo() == null ? 0 : this.getNoticeNo().hashCode() );
         result = 37 * result + ( getDate() == null ? 0 : this.getDate().hashCode() );
         result = 37 * result + ( getNoticeType() == null ? 0 : this.getNoticeType().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         return result;
   }   





}