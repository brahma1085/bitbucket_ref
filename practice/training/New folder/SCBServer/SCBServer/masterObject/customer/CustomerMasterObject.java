package masterObject.customer;

import masterObject.general.Address;
import masterObject.general.UserVerifier;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;



public class CustomerMasterObject implements Serializable,Cloneable
{
    
    int cid;
    String fname,mname,lname,others,panno,gname,occup,dob,gaddress;
    String sex,gsex,maritals,salute,gsalute,custtype,nameproof,addrproof,suboccup,faname,maname,husname;
    int op,category,subcat;
    String introno,ppno,issuedate,expirydate,scst,sign,photo,grelation,courtdate,gtype,caste,ve_user,ve_tml,ve_date;
    byte bimage[],bsimage[];
    HashMap address=new HashMap();
    
    public UserVerifier uv=new UserVerifier();
    private String nationality;
    private String religion,br_code;
    
    
    public void copy(CustomerMasterObject cm1,CustomerMasterObject cm)
    {
        try{
            cm1=(CustomerMasterObject)cm.clone();
            
            //cm1.address=(HashMap)cm.getAddress().clone();
            System.out.println("Inside copy command"+cm1.address.size() );
        }catch(Exception ex){ex.printStackTrace();}
        
    }
    
    public void copy(CustomerMasterObject cm)
    {
        cid=cm.cid;
        fname=cm.fname;
        mname=cm.mname;
        lname=cm.lname;
        others=cm.others;
        panno=cm.panno;
        gname=cm.gname;
        occup=cm.occup;
        dob=cm.dob;
        gaddress=cm.gaddress;
        sex=cm.sex;
        gsex=cm.gsex;
        maritals=cm.maritals;
        salute=cm.salute;
        gsalute=cm.gsalute;
        custtype=cm.custtype;
        introno=cm.introno;
        ppno=cm.ppno;
        issuedate=cm.issuedate;
        expirydate=cm.expirydate;
        scst=cm.scst;
        sign=cm.sign;
        photo=cm.photo;
        grelation=cm.grelation;
        suboccup=cm.suboccup;
        addrproof=cm.addrproof;
        nameproof=cm.nameproof;
        
        address=new HashMap();
        
        address=(HashMap)cm.getAddress().clone();	
        System.out.println("After copy of data");
        
    }
    
    public  boolean equals(CustomerMasterObject customermasterobject_old)
    {
        boolean flag=false;
        System.out.println("Before alling");
        breakiftrue:{
            if(getIntroducerId()!=null && customermasterobject_old.getIntroducerId()!=null)
            {		
                if(!getIntroducerId().equals(customermasterobject_old.getIntroducerId()))
                    flag=true;
            }
            else if(getIntroducerId()!=null || customermasterobject_old.getIntroducerId()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("1....................");
            
            if(getFirstName()!=null && customermasterobject_old.getFirstName()!=null)
            {
                if(!getFirstName().equals(customermasterobject_old.getFirstName()))
                    flag=true;
            }
            else if(getFirstName()!=null || customermasterobject_old.getFirstName()!=null)
                flag=true;
            
            if(flag==true)
                break breakiftrue;
            System.out.println("2....................");
            
            
            if(getMiddleName()!=null && customermasterobject_old.getMiddleName()!=null)
            {
                if(!getMiddleName().equals(customermasterobject_old.getMiddleName()))
                    flag=true;
            }
            else if(getMiddleName()!=null || customermasterobject_old.getMiddleName()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            
            System.out.println("3....................");
            
            if(getLastName()!=null && customermasterobject_old.getLastName()!=null)
            {
                if(!getLastName().equals(customermasterobject_old.getLastName()))
                    flag=true;
            }
            else if(getLastName()!=null || customermasterobject_old.getLastName()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            
            System.out.println("4....................");
            
            if(getSalute()!=null && customermasterobject_old.getSalute()!=null)
            {
                if(!getSalute().equals(customermasterobject_old.getSalute()))
                    flag=true;
            }
            else if(getSalute()!=null || customermasterobject_old.getSalute()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            
            System.out.println("5...................");
            
            if(getPanno()!=null && customermasterobject_old.getPanno()!=null)
            {
                if(!getPanno().equals(customermasterobject_old.getPanno()))
                    flag=true;
            }
            else if(getPanno()!=null || customermasterobject_old.getPanno()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("6....................");
            
            if(getPassPortNumber()!=null && customermasterobject_old.getPassPortNumber()!=null)
            {
                if(!getPassPortNumber().equals(customermasterobject_old.getPassPortNumber()))
                    flag=true;
            }
            else if(getPassPortNumber()!=null || customermasterobject_old.getPassPortNumber()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("7....................");
            
            
            if(getPPIssueDate()!=null && customermasterobject_old.getPPIssueDate()!=null)
            {
                if(!getPPIssueDate().equals(customermasterobject_old.getPPIssueDate()))
                    flag=true;
            }
            else if(getPPIssueDate()!=null || customermasterobject_old.getPPIssueDate()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("8....................");
            
            
            if(getPPExpiryDate()!=null && customermasterobject_old.getPPExpiryDate()!=null)
            {
                if(!getPPExpiryDate().equals(customermasterobject_old.getPPExpiryDate()))
                    flag=true;
            }
            else if(getPPExpiryDate()!=null || customermasterobject_old.getPPExpiryDate()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("9....................");
            
            
            if(getSex()!=null && customermasterobject_old.getSex()!=null)
            {
                if(!getSex().equals(customermasterobject_old.getSex()))
                    flag=true;
            }
            else if(getSex()!=null || customermasterobject_old.getSex()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("10....................");
            
            if(getMaritalStatus()!=null && customermasterobject_old.getMaritalStatus()!=null)
            {
                if(!getMaritalStatus().equals(customermasterobject_old.getMaritalStatus()))
                    flag=true;
            }
            else if(getMaritalStatus()!=null || customermasterobject_old.getMaritalStatus()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            
            System.out.println("11....................");
            
            if(getScSt()!=null && customermasterobject_old.getScSt()!=null)
            {
                if(!getScSt().equals(customermasterobject_old.getScSt()))
                    flag=true;
            }
            else if(getScSt()!=null || customermasterobject_old.getScSt()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            
            System.out.println("12....................");
            
            if(getGuardName()!=null && customermasterobject_old.getGuardName()!=null)
            {
                if(!getGuardName().equals(customermasterobject_old.getGuardName()))
                    flag=true;
            }
            else if(getGuardName()!=null || customermasterobject_old.getGuardName()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("13....................");
            
            if(getGuardSalute()!=null && customermasterobject_old.getGuardSalute()!=null)
            {
                if(!getGuardSalute().equals(customermasterobject_old.getGuardSalute()))
                    flag=true;
            }
            else if(getGuardSalute()!=null || customermasterobject_old.getGuardSalute()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("14...................");
            
            if(getGuardSex()!=null && customermasterobject_old.getGuardSex()!=null)
            {
                if(!getGuardSex().equals(customermasterobject_old.getGuardSex()))
                    flag=true;
            }
            else if(getGuardSex()!=null || customermasterobject_old.getGuardSex()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("15....................");
            
            
            if(getGuardAddress()!=null && customermasterobject_old.getGuardAddress()!=null)
            {
                if(!getGuardAddress().equals(customermasterobject_old.getGuardAddress()))
                    flag=true;
            }
            else if(getGuardAddress()!=null || customermasterobject_old.getGuardAddress()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("16....................");
            
            
            if(getGuardRelationship()!=null && customermasterobject_old.getGuardRelationship()!=null)
            {
                if(!getGuardRelationship().equals(customermasterobject_old.getGuardRelationship()))
                    flag=true;
            }
            else if(getGuardRelationship()!=null || customermasterobject_old.getGuardRelationship()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("17...................");
            
            if(getSubOccupation()!=null && customermasterobject_old.getSubOccupation()!=null)
            {
                if(!getSubOccupation().equals(customermasterobject_old.getSubOccupation()))
                    flag=true;
            }
            else if(getSubOccupation()!=null || customermasterobject_old.getSubOccupation()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            
            System.out.println("18...................");
            
            if(getAddressProof()!=null && customermasterobject_old.getAddressProof()!=null)
            {
                if(!getAddressProof().equals(customermasterobject_old.getAddressProof()))
                    flag=true;
            }
            else if(getAddressProof()!=null || customermasterobject_old.getAddressProof()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            
            System.out.println("19....................");
            
            if(getNameProof()!=null && customermasterobject_old.getNameProof()!=null)
            {
                if(!getNameProof().equals(customermasterobject_old.getNameProof()))
                    flag=true;
            }
            else if(getNameProof()!=null || customermasterobject_old.getNameProof()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("20...................");
            
            
            if(getOccupation()!=null && customermasterobject_old.getOccupation()!=null)
            {
                if(!getOccupation().equals(customermasterobject_old.getOccupation()))
                    flag=true;
            }
            else if(getOccupation()!=null || customermasterobject_old.getOccupation()!=null)
                flag=true;
            if(flag==true)
                break breakiftrue;
            System.out.println("21....................");
        }
        System.out.println("Flag"+flag);
        return flag;
    }
    
    public boolean addressIsEqual(HashMap customermasterobject_old)
    {
        if(customermasterobject_old!=null)
        {	
            Set new_address=getAddress().keySet();
            
            Iterator iterator_new_address=new_address.iterator() ;
            
            aa:while(iterator_new_address.hasNext())
            {
                Integer i=(Integer)iterator_new_address.next();
                Address master_new=(Address)this.getAddress().get(i);
                Address master_old=(Address)customermasterobject_old.get(i);
                boolean flag=false;
                
                if(master_old==null)
                    master_new.setChanged(true);
                else
                {
                    breakiftrue:{
                        System.out.println("addr1....................");
                        if(master_new.getAddress()!=null && master_old.getAddress()!=null)
                        {
                            if(!master_new.getAddress().equals(master_old.getAddress()))
                                flag=true;
                        }
                        else if(master_new.getAddress()!=null || master_old.getAddress()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr2....................");
                        if(master_new.getCity()!=null && master_old.getCity()!=null)
                        {
                            if(!master_new.getCity().equals(master_old.getCity()))
                                flag=true;
                        }
                        else if(master_new.getCity()!=null || master_old.getCity()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr3....................");
                        if(master_new.getState()!=null && master_old.getState()!=null)
                        {
                            if(!master_new.getState().equals(master_old.getState()))
                                flag=true;
                        }
                        else if(master_new.getState()!=null || master_old.getState()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr4....................");
                        if(master_new.getPin()!=null && master_old.getPin()!=null)
                        {	
                            if(!master_new.getPin().equals(master_old.getPin()))
                                flag=true;
                        }
                        else if(master_new.getPin()!=null || master_old.getPin()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr5....................");
                        if(master_new.getCountry()!=null && master_old.getCountry()!=null)
                        {
                            if(!master_new.getCountry().equals(master_old.getCountry()))
                                flag=true;
                        }
                        else if(master_new.getCountry()!=null || master_old.getCountry()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr6....................");
                        if(master_new.getPhno()!=null && master_old.getPhno()!=null)
                        {
                            if(!master_new.getPhno().equals(master_old.getPhno()))
                                flag=true;
                        }
                        else if(master_new.getPhno()!=null || master_old.getPhno()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr7....................");
                        if(master_new.getPhStd()!=null && master_old.getPhStd()!=null)
                        {
                            if(!master_new.getPhStd().equals(master_old.getPhStd()))
                                flag=true;
                        }
                        else if(master_new.getPhStd()!=null || master_old.getPhStd()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr8....................");	
                        if(master_new.getFax()!=null && master_old.getFax()!=null)
                        {
                            if(!master_new.getFax().equals(master_old.getFax()))
                                flag=true;
                        }
                        else if(master_new.getFax()!=null || master_old.getFax()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr9....................");	
                        if(master_new.getFaxStd()!=null && master_old.getFaxStd()!=null)
                        {
                            if(!master_new.getFaxStd().equals(master_old.getFaxStd()))
                                flag=true;
                        }
                        else if(master_new.getFaxStd()!=null || master_old.getFaxStd()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr10....................");
                        if(master_new.getMobile()!=null && master_old.getMobile()!=null)
                        {
                            if(!master_new.getMobile().equals(master_old.getMobile()))
                                flag=true;
                        }
                        else if(master_new.getMobile()!=null || master_old.getMobile()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                        
                        System.out.println("addr11....................");
                        if(master_new.getEmail()!=null && master_old.getEmail()!=null)
                        {
                            if(!master_new.getEmail().equals(master_old.getEmail()))
                                flag=true;
                        }
                        else if(master_new.getEmail()!=null || master_old.getEmail()!=null)
                            flag=true;
                        if(flag==true)
                            break breakiftrue;
                    }
                System.out.println("Addr12..............."+flag);
                if(flag==true)
                {
                    ((Address)this.getAddress().get(i)).setChanged(true);		
                    System.out.println("changed address"+i);
                }
                }
            }	
        }
        return true;
    }
    
    
    public void setCategory(int s){category=s;}
    public int getCategory(){return category;}
    
    public void setAddress(HashMap hs){address=hs;}
    public HashMap getAddress(){return address;}
    
    
    public void setBinaryImage(byte str[]){bimage=str;}
    public byte[] getBinaryImage(){return bimage;}
    
    public void setBinarySignImage(byte str[]){bsimage=str;}
    public byte[] getBinarySignImage(){return bsimage;}
    
    public int getSubCategory(){return subcat;}
    public void setSubCategory(int a){subcat=a;}
    
    public void setScSt(String str){scst=str;}
    public String getScSt(){return scst;}
    
    public void setCaste(String str){caste=str;}
    public String getCaste(){return caste;}
    
    
    public void setPassPortNumber(String str){ppno=str;}
    public String getPassPortNumber(){return ppno;}
    
    public void setFatherName(String fanme){this.faname=fanme;}
    public String getFatherName(){return faname;}
    
    public void setMotherName(String fanme){this.maname=fanme;}
    public String getMotherName(){return maname;}
    
    
    public void setPPIssueDate(String str){issuedate=str;}
    public String getPPIssueDate(){return issuedate;}
    
    public void setPPExpiryDate(String str){expirydate=str;}
    public String getPPExpiryDate(){return expirydate;}
    
    
    public void setOperation(int op){this.op=op;}
    public int getOperation(){return op;}
    
    
    public String getAddressProof(){return addrproof;}
    public void  setAddressProof(String type){addrproof=type;}
    
    public String getNameProof(){return nameproof;}
    public void  setNameProof(String type){nameproof=type;}
    
    public String getSubOccupation(){return suboccup;}
    public void  setSubOccupation(String type){suboccup=type;}
    
    public String getIntroducerId(){return introno;}
    public void  setIntroducerId(String type){introno=type;}
    
    
    public int getCustomerID(){return cid;}
    public void setCustomerID(int cid){this.cid=cid;}
    
    public String getFirstName(){return fname;}
    public void setFirstName(String fname){this.fname=fname;}
    
    public String getMiddleName(){return mname;}
    public void setMiddleName(String mname){this.mname=mname;}
    
    public String getLastName(){return lname;}
    public void setLastName(String lname){this.lname=lname;}
    
    public String getName(){return (fname+" "+mname+" "+lname);}
    
    public String getSalute(){return salute;}
    public void setSalute(String salute){this.salute=salute;}
    
    public String getDOB(){return dob;}
    public void setDOB(String dob){this.dob=dob;}
    
    public String getSex(){return sex;}
    public void setSex(String sex){this.sex=sex;}
    
    public String getMaritalStatus(){return maritals;}
    public void setMaritalStatus(String maritals){this.maritals=maritals;}
    
    
    public String getPanno(){return panno;}
    public void setPanno(String panno){this.panno=panno;}
    
    public String getOccupation(){return occup;}
    public void setOccupation(String occup){this.occup=occup;}
    
    public String getGuardName(){return gname;}
    public void setGuardName(String gname){this.gname=gname;}
    
    public String getGuardSalute(){return gsalute;}
    public void setGuardSalute(String gsalute){this.gsalute=gsalute;}
    
    public String getGuardAddress(){return gaddress;}
    public void setGuardAddress(String gaddress){this.gaddress=gaddress;}
    
    public String getGuardSex(){return gsex;}
    public void setGuardSex(String gsex){this.gsex=gsex;}
    
    public String getGuardRelationship(){return grelation;}
    public void setGuardRelationship(String rel){grelation=rel;}
    
    public String getGuardType(){return gtype;}
    public void setGuardType(String gaddress){this.gtype=gaddress;}
    
    public String getCourtDate(){return courtdate;}
    public void setCourtDate(String rel){courtdate=rel;}
    
    public String getPhoto(){ return photo;}
    public void setPhoto(String photo){this.photo=photo;}
    
    public String getSign(){ return sign;}
    public void setSign(String sign){this.sign=sign;}
    
    public String getSpouseName() {
        
        return husname;
    }
    
    public void setSpouseName(String h) {	
        husname=h;
    }
    
    public String getNationality() {
        return nationality;
    }
    
    public void setNationality(String na) {
        nationality=na;
    }
    
    public String getReligion() {
        return religion;
    }
    public void setReligion(String str)
    {
        religion=str;
    }
    public void setBranchCode(String br_code){this.br_code=br_code;}
    public String getBranchCode(){return br_code;}

	public String getVe_user() {
		return ve_user;
	}

	public void setVe_user(String ve_user) {
		this.ve_user = ve_user;
	}

	public String getVe_tml() {
		return ve_tml;
	}

	public void setVe_tml(String ve_tml) {
		this.ve_tml = ve_tml;
	}

	public String getVe_date() {
		return ve_date;
	}

	public void setVe_date(String ve_date) {
		this.ve_date = ve_date;
	}
}

