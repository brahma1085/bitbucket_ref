package com.banking.data.pojo;
// default package



/**
 * PSNmMstId entity. @author MyEclipse Persistence Tools
 */

public class PSNmMstId  implements java.io.Serializable {


    // Fields    

     private String nameKey;
     private String name;
     private String dofBirth;
     private String age;
     private String occupation;
     private String caste;
     private String fhName;
     private String telNos;
     private String deTml;
     private String deUser;
     private String deDate;
     private String deTime;


    // Constructors

    /** default constructor */
    public PSNmMstId() {
    }

    
    /** full constructor */
    public PSNmMstId(String nameKey, String name, String dofBirth, String age, String occupation, String caste, String fhName, String telNos, String deTml, String deUser, String deDate, String deTime) {
        this.nameKey = nameKey;
        this.name = name;
        this.dofBirth = dofBirth;
        this.age = age;
        this.occupation = occupation;
        this.caste = caste;
        this.fhName = fhName;
        this.telNos = telNos;
        this.deTml = deTml;
        this.deUser = deUser;
        this.deDate = deDate;
        this.deTime = deTime;
    }

   
    // Property accessors

    public String getNameKey() {
        return this.nameKey;
    }
    
    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDofBirth() {
        return this.dofBirth;
    }
    
    public void setDofBirth(String dofBirth) {
        this.dofBirth = dofBirth;
    }

    public String getAge() {
        return this.age;
    }
    
    public void setAge(String age) {
        this.age = age;
    }

    public String getOccupation() {
        return this.occupation;
    }
    
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCaste() {
        return this.caste;
    }
    
    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getFhName() {
        return this.fhName;
    }
    
    public void setFhName(String fhName) {
        this.fhName = fhName;
    }

    public String getTelNos() {
        return this.telNos;
    }
    
    public void setTelNos(String telNos) {
        this.telNos = telNos;
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
		 if ( !(other instanceof PSNmMstId) ) return false;
		 PSNmMstId castOther = ( PSNmMstId ) other; 
         
		 return ( (this.getNameKey()==castOther.getNameKey()) || ( this.getNameKey()!=null && castOther.getNameKey()!=null && this.getNameKey().equals(castOther.getNameKey()) ) )
 && ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getDofBirth()==castOther.getDofBirth()) || ( this.getDofBirth()!=null && castOther.getDofBirth()!=null && this.getDofBirth().equals(castOther.getDofBirth()) ) )
 && ( (this.getAge()==castOther.getAge()) || ( this.getAge()!=null && castOther.getAge()!=null && this.getAge().equals(castOther.getAge()) ) )
 && ( (this.getOccupation()==castOther.getOccupation()) || ( this.getOccupation()!=null && castOther.getOccupation()!=null && this.getOccupation().equals(castOther.getOccupation()) ) )
 && ( (this.getCaste()==castOther.getCaste()) || ( this.getCaste()!=null && castOther.getCaste()!=null && this.getCaste().equals(castOther.getCaste()) ) )
 && ( (this.getFhName()==castOther.getFhName()) || ( this.getFhName()!=null && castOther.getFhName()!=null && this.getFhName().equals(castOther.getFhName()) ) )
 && ( (this.getTelNos()==castOther.getTelNos()) || ( this.getTelNos()!=null && castOther.getTelNos()!=null && this.getTelNos().equals(castOther.getTelNos()) ) )
 && ( (this.getDeTml()==castOther.getDeTml()) || ( this.getDeTml()!=null && castOther.getDeTml()!=null && this.getDeTml().equals(castOther.getDeTml()) ) )
 && ( (this.getDeUser()==castOther.getDeUser()) || ( this.getDeUser()!=null && castOther.getDeUser()!=null && this.getDeUser().equals(castOther.getDeUser()) ) )
 && ( (this.getDeDate()==castOther.getDeDate()) || ( this.getDeDate()!=null && castOther.getDeDate()!=null && this.getDeDate().equals(castOther.getDeDate()) ) )
 && ( (this.getDeTime()==castOther.getDeTime()) || ( this.getDeTime()!=null && castOther.getDeTime()!=null && this.getDeTime().equals(castOther.getDeTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getNameKey() == null ? 0 : this.getNameKey().hashCode() );
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getDofBirth() == null ? 0 : this.getDofBirth().hashCode() );
         result = 37 * result + ( getAge() == null ? 0 : this.getAge().hashCode() );
         result = 37 * result + ( getOccupation() == null ? 0 : this.getOccupation().hashCode() );
         result = 37 * result + ( getCaste() == null ? 0 : this.getCaste().hashCode() );
         result = 37 * result + ( getFhName() == null ? 0 : this.getFhName().hashCode() );
         result = 37 * result + ( getTelNos() == null ? 0 : this.getTelNos().hashCode() );
         result = 37 * result + ( getDeTml() == null ? 0 : this.getDeTml().hashCode() );
         result = 37 * result + ( getDeUser() == null ? 0 : this.getDeUser().hashCode() );
         result = 37 * result + ( getDeDate() == null ? 0 : this.getDeDate().hashCode() );
         result = 37 * result + ( getDeTime() == null ? 0 : this.getDeTime().hashCode() );
         return result;
   }   





}