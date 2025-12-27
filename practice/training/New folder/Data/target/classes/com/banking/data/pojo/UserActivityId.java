package com.banking.data.pojo;
// default package



/**
 * UserActivityId entity. @author MyEclipse Persistence Tools
 */

public class UserActivityId  implements java.io.Serializable {


    // Fields    

     private String userId;
     private String tmlNo;
     private String ipAddress;
     private String pageVisit;
     private String activity;
     private String acType;
     private Integer acNo;
     private Integer cid;
     private String activityDate;
     private String activityTime;


    // Constructors

    /** default constructor */
    public UserActivityId() {
    }

    
    /** full constructor */
    public UserActivityId(String userId, String tmlNo, String ipAddress, String pageVisit, String activity, String acType, Integer acNo, Integer cid, String activityDate, String activityTime) {
        this.userId = userId;
        this.tmlNo = tmlNo;
        this.ipAddress = ipAddress;
        this.pageVisit = pageVisit;
        this.activity = activity;
        this.acType = acType;
        this.acNo = acNo;
        this.cid = cid;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTmlNo() {
        return this.tmlNo;
    }
    
    public void setTmlNo(String tmlNo) {
        this.tmlNo = tmlNo;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPageVisit() {
        return this.pageVisit;
    }
    
    public void setPageVisit(String pageVisit) {
        this.pageVisit = pageVisit;
    }

    public String getActivity() {
        return this.activity;
    }
    
    public void setActivity(String activity) {
        this.activity = activity;
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

    public Integer getCid() {
        return this.cid;
    }
    
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getActivityDate() {
        return this.activityDate;
    }
    
    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityTime() {
        return this.activityTime;
    }
    
    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof UserActivityId) ) return false;
		 UserActivityId castOther = ( UserActivityId ) other; 
         
		 return ( (this.getUserId()==castOther.getUserId()) || ( this.getUserId()!=null && castOther.getUserId()!=null && this.getUserId().equals(castOther.getUserId()) ) )
 && ( (this.getTmlNo()==castOther.getTmlNo()) || ( this.getTmlNo()!=null && castOther.getTmlNo()!=null && this.getTmlNo().equals(castOther.getTmlNo()) ) )
 && ( (this.getIpAddress()==castOther.getIpAddress()) || ( this.getIpAddress()!=null && castOther.getIpAddress()!=null && this.getIpAddress().equals(castOther.getIpAddress()) ) )
 && ( (this.getPageVisit()==castOther.getPageVisit()) || ( this.getPageVisit()!=null && castOther.getPageVisit()!=null && this.getPageVisit().equals(castOther.getPageVisit()) ) )
 && ( (this.getActivity()==castOther.getActivity()) || ( this.getActivity()!=null && castOther.getActivity()!=null && this.getActivity().equals(castOther.getActivity()) ) )
 && ( (this.getAcType()==castOther.getAcType()) || ( this.getAcType()!=null && castOther.getAcType()!=null && this.getAcType().equals(castOther.getAcType()) ) )
 && ( (this.getAcNo()==castOther.getAcNo()) || ( this.getAcNo()!=null && castOther.getAcNo()!=null && this.getAcNo().equals(castOther.getAcNo()) ) )
 && ( (this.getCid()==castOther.getCid()) || ( this.getCid()!=null && castOther.getCid()!=null && this.getCid().equals(castOther.getCid()) ) )
 && ( (this.getActivityDate()==castOther.getActivityDate()) || ( this.getActivityDate()!=null && castOther.getActivityDate()!=null && this.getActivityDate().equals(castOther.getActivityDate()) ) )
 && ( (this.getActivityTime()==castOther.getActivityTime()) || ( this.getActivityTime()!=null && castOther.getActivityTime()!=null && this.getActivityTime().equals(castOther.getActivityTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getUserId() == null ? 0 : this.getUserId().hashCode() );
         result = 37 * result + ( getTmlNo() == null ? 0 : this.getTmlNo().hashCode() );
         result = 37 * result + ( getIpAddress() == null ? 0 : this.getIpAddress().hashCode() );
         result = 37 * result + ( getPageVisit() == null ? 0 : this.getPageVisit().hashCode() );
         result = 37 * result + ( getActivity() == null ? 0 : this.getActivity().hashCode() );
         result = 37 * result + ( getAcType() == null ? 0 : this.getAcType().hashCode() );
         result = 37 * result + ( getAcNo() == null ? 0 : this.getAcNo().hashCode() );
         result = 37 * result + ( getCid() == null ? 0 : this.getCid().hashCode() );
         result = 37 * result + ( getActivityDate() == null ? 0 : this.getActivityDate().hashCode() );
         result = 37 * result + ( getActivityTime() == null ? 0 : this.getActivityTime().hashCode() );
         return result;
   }   





}